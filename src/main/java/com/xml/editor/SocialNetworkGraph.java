package com.xml.editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
/**
 * The {@code SocialNetworkGraph} class represents a social network where users can be modeled
 * as vertices in a graph, with relationships (followers) represented as directed edges between
 * the users. The class provides methods for building the graph from an XML representation,
 * visualizing it, and saving the graph as an image.
 */
class SocialNetworkGraph {
    Map<Integer, User> users;
    Graph graph;

    SocialNetworkGraph() {
        this.users = new HashMap<>();
        this.graph = new Graph();
    }
    /**
     * Builds the social network graph from an XML representation.
     * This method parses the XML string and extracts user information, including their IDs, names,
     * posts, and followers, and populates the {@code users} map and the {@code graph}.
     *
     * @param xml the XML string representing the social network data.
     */
    public void buildGraphFromXML(String xml) {
        xml = xml.replaceAll("\\s*\n\\s*", ""); // Clean up input XML

        Pattern userPattern = Pattern.compile("<user>(.*?)</user>");
        Pattern idPattern = Pattern.compile("<id>(\\d+)</id>"); // Match numeric IDs
        Pattern namePattern = Pattern.compile("<name>(.*?)</name>");
        Pattern postPattern = Pattern.compile("<body>(.*?)</body>");
        Pattern followerIdPattern = Pattern.compile("<follower>\\s*<id>(\\d+)</id>\\s*</follower>"); // Match <id> inside <follower>

        Matcher userMatcher = userPattern.matcher(xml);

        while (userMatcher.find()) {
            String userBlock = userMatcher.group(1);

            Matcher idMatcher = idPattern.matcher(userBlock);
            int userId = idMatcher.find() ? Integer.parseInt(idMatcher.group(1)) : -1;

            Matcher nameMatcher = namePattern.matcher(userBlock);
            String userName = nameMatcher.find() ? nameMatcher.group(1) : "Unknown";

            User user = new User(userId, userName);

            Matcher postMatcher = postPattern.matcher(userBlock);
            while (postMatcher.find()) {
                user.posts.add(postMatcher.group(1));
            }

            Matcher followerMatcher = followerIdPattern.matcher(userBlock);
            while (followerMatcher.find()) {
                int followerId = Integer.parseInt(followerMatcher.group(1));
                user.followers.add(followerId);
                graph.addEdge(followerId, userId); // Add to graph as a directed edge
            }

            users.put(userId, user);
            graph.addVertex(userId); // Add the user as a vertex
        }
    }    /**
     * Returns a string representation of all users in the social network.
     *
     * @return a string listing all users in the social network.
     */

    public String printUsers() {
        if (users.isEmpty()) {
            return ("No users found in the graph.");

        }
        StringBuilder temp = new StringBuilder();

        for (User user : users.values()) {
            temp.append(user);
            temp.append("--------------------");
        }
        return ("Social Network Users:" + temp);
    }

    /**
     * Returns a string representation of the entire graph.
     *
     * @return the graph's string representation.
     */
    public String printGraph() {
        return graph.printGraph();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user.
     * @return the {@code User} object corresponding to the provided ID, or {@code null} if not found.
     */
    public User getUserById(int id) {
        return users.get(id);
    }

    /**
     * Returns the total number of users in the social network.
     *
     * @return the number of users in the network.
     */
    public int getTotalUsers() {
        return users.size();
    }

    /**
     * Draws the graph on a canvas with a circular layout and edge connections.
     *
     * @param canvasWidth  the width of the canvas.
     * @param canvasHeight the height of the canvas.
     * @return a {@code Canvas} object representing the graph's visualization.
     */

    public Canvas drawGraphOnCanvas(int canvasWidth, int canvasHeight) {
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Get the vertices
        Set<Integer> vertices = graph.getVertices();
        int totalVertices = vertices.size();

        if (totalVertices == 0) {
            gc.setFill(Color.BLACK);
            gc.fillText("Graph is empty!", canvasWidth / 2.0, canvasHeight / 2.0);
            return canvas;
        }

        // Calculate positions for vertices in a circular layout
        double centerX = canvasWidth / 2.0;
        double centerY = canvasHeight / 2.0;
        double radius = Math.min(canvasWidth, canvasHeight) / 3.5; // Adjusted radius for spacing

        // Map each vertex to a position
        Map<Integer, double[]> positions = new HashMap<>();
        int index = 0;
        for (int vertex : vertices) {
            double angle = 2 * Math.PI * index / totalVertices;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            positions.put(vertex, new double[]{x, y});
            index++;
        }

        // Draw edges with arrows
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1.5);
        for (int from : vertices) {
            List<Integer> adjacencyList = graph.getAdjacencyList(from);
            double[] fromPos = positions.get(from);

            for (int to : adjacencyList) {
                double[] toPos = positions.get(to);

                // Draw the edge line
                gc.strokeLine(fromPos[0], fromPos[1], toPos[0], toPos[1]);

                // Draw the arrowhead
                drawArrow(gc, fromPos[0], fromPos[1], toPos[0], toPos[1]);
            }
        }
        // Draw vertices with larger circles
        gc.setFill(Color.BLUE);
        gc.setStroke(Color.BLACK);
        double circleRadius = 30; // Increased circle radius (diameter is 2 * radius)

        for (Map.Entry<Integer, double[]> entry : positions.entrySet()) {
            int vertex = entry.getKey();
            double[] pos = entry.getValue();
            double x = pos[0];
            double y = pos[1];

            // Generate a unique color for each vertex
            Color vertexColor = Color.color(Math.random(), Math.random(), Math.random()); // Random unique colors

            // Draw vertex circle with the assigned color
            gc.setFill(vertexColor);
            gc.fillOval(x - circleRadius, y - circleRadius, 2 * circleRadius, 2 * circleRadius);
            gc.setStroke(Color.BLACK);
            gc.strokeOval(x - circleRadius, y - circleRadius, 2 * circleRadius, 2 * circleRadius);

            // Set font size for vertex label
            gc.setFill(Color.WHITE); // Use white text for contrast
            gc.setFont(new Font("Arial", 16)); // Increase font size here
            gc.fillText(String.valueOf(vertex), x - 5, y + 5);
        }


        return canvas;
    }

    /**
     * Helper method to draw an arrowhead at the end of an edge.
     *
     * @param gc    the {@code GraphicsContext} to use for drawing.
     * @param fromX the x-coordinate of the starting point of the arrow.
     * @param fromY the y-coordinate of the starting point of the arrow.
     * @param toX   the x-coordinate of the endpoint of the arrow.
     * @param toY   the y-coordinate of the endpoint of the arrow.
     */
    // Helper method to draw an arrowhead, adjusted for larger circles
    private void drawArrow(GraphicsContext gc, double fromX, double fromY, double toX, double toY) {
        double arrowLength = 12; // Increased length of the arrowhead
        double arrowWidth = 8;   // Width of the arrowhead

        // Calculate the angle of the line
        double angle = Math.atan2(toY - fromY, toX - fromX);

        // Calculate the tip of the arrow (further away from the circle)
        double tipX = toX - 30 * Math.cos(angle); // Adjust the distance to move the tip away from the circle
        double tipY = toY - 30 * Math.sin(angle); // Adjust the distance to move the tip away from the circle

        // Calculate the two base points of the arrowhead
        double x1 = tipX - arrowLength * Math.cos(angle - Math.PI / 6);
        double y1 = tipY - arrowLength * Math.sin(angle - Math.PI / 6);

        double x2 = tipX - arrowLength * Math.cos(angle + Math.PI / 6);
        double y2 = tipY - arrowLength * Math.sin(angle + Math.PI / 6);

        // Draw the arrowhead
        gc.setFill(Color.GRAY);
        gc.fillPolygon(new double[]{tipX, x1, x2}, new double[]{tipY, y1, y2}, 3);
    }
    /**
     * Saves the contents of a {@link Canvas} as a JPG image file.
     * <p>
     * This method allows the user to select a location and filename to save the canvas as a JPG image.
     * It captures the current content of the canvas, converts it to a {@link BufferedImage}, and saves it to a file.
     * The file extension is checked and ".jpg" will be appended if necessary.
     * </p>
     *
     * @param canvas The {@link Canvas} whose content will be saved as a JPG image.
     *               The method takes a snapshot of the canvas and converts it into an image file.
     *
     * @throws IOException If an error occurs while writing the image to the file.
     */
    public static void saveCanvasAsJPG(Canvas canvas) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("image", "*.jpg")
        );
        File saveFile = fileChooser.showSaveDialog(canvas.getScene().getWindow());
        if (saveFile != null) {
            try {
                String filePath = saveFile.getPath();
                // Create a WritableImage matching the size of the Canvas
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());

                // Snapshot the Canvas contents into the WritableImage
                canvas.snapshot(null, writableImage);

                // Convert WritableImage to BufferedImage manually
                BufferedImage bufferedImage = new BufferedImage(
                        (int) canvas.getWidth(),
                        (int) canvas.getHeight(),
                        BufferedImage.TYPE_INT_RGB
                );

                // Copy pixel data from WritableImage to BufferedImage
                for (int x = 0; x < canvas.getWidth(); x++) {
                    for (int y = 0; y < canvas.getHeight(); y++) {
                        javafx.scene.paint.Color fxColor = writableImage.getPixelReader().getColor(x, y);
                        java.awt.Color awtColor = new java.awt.Color(
                                (float) fxColor.getRed(),
                                (float) fxColor.getGreen(),
                                (float) fxColor.getBlue()
                        );
                        bufferedImage.setRGB(x, y, awtColor.getRGB());
                    }
                }

                // Save the BufferedImage as a JPG file
                File outputFile = new File(filePath);
                ImageIO.write(bufferedImage, "jpg", outputFile);
                System.out.println("Canvas saved as JPG: " + filePath);
            }
            catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error saving canvas as JPG: " + e.getMessage());
            }
        }
    }
}
