package com.automoto.util;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.http.Part;

/**
 * Utility class for handling image file uploads in a web application.
 * <p>
 * Provides methods for processing file uploads through servlet parts, including
 * filename extraction and file system storage operations. The class handles
 * directory creation and file writing operations with appropriate error handling.
 * </p>
 */
public class ImageUtil {

    /**
     * Extracts the filename from a multipart form data part's content-disposition header.
     * The method parses the header to find the original filename submitted by the client.
     * If no filename can be determined, returns a default filename "download.png".
     *
     * @param part the Part object containing the file upload data
     * @return the extracted filename or "download.png" if no filename found
     */
    public String getImageNameFromPart(Part part) {
        // Retrieve the content-disposition header from the part
        String contentDisp = part.getHeader("content-disposition");

        // Split the header by semicolons to isolate key-value pairs
        String[] items = contentDisp.split(";");

        // Initialize imageName variable to store the extracted file name
        String imageName = null;

        // Iterate through the items to find the filename
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                // Extract the file name from the header value
                imageName = s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }

        // Check if the filename was not found or is empty
        if (imageName == null || imageName.isEmpty()) {
            // Assign a default file name if none was provided
            imageName = "download.png";
        }

        // Return the extracted or default file name
        return imageName;
    }

    /**
     * Uploads an image file to the server's filesystem.
     * Creates the target directory if it doesn't exist and writes the uploaded file.
     * Uses {@link #getImageNameFromPart(Part)} to determine the filename and
     * {@link #getSavePath(String)} to determine the storage location.
     *
     * @param part the Part object containing the file data to upload
     * @param rootPath the root path of the web application (unused in current implementation)
     * @param saveFolder the subfolder within the images directory where the file should be saved
     * @return true if the upload succeeded, false if directory creation failed or an IOException occurred
     */
    public boolean uploadImage(Part part, String rootPath, String saveFolder) {
        String savePath = getSavePath(saveFolder);
        File fileSaveDir = new File(savePath);

        // Ensure the directory exists
        if (!fileSaveDir.exists()) {
            if (!fileSaveDir.mkdir()) {
                return false; // Failed to create the directory
            }
        }
        try {
            // Get the image name
            String imageName = getImageNameFromPart(part);
            // Create the file path
            String filePath = savePath + "/" + imageName;
            // Write the file to the server
            part.write(filePath);
            return true; // Upload successful
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception
            return false; // Upload failed
        }
    }

    /**
     * Constructs the absolute filesystem path for storing uploaded images.
     * The path is fixed to a specific location within the web application's
     * resources directory, with the provided folder name appended.
     *
     * @param saveFolder the name of the subfolder within the system images directory
     * @return the complete absolute path as a String
     */
    public String getSavePath(String saveFolder) {
        return "/Users/asu/eclipse-workspace/AutoMoto/src/main/webapp/resources/images/system/" + saveFolder + "/";
    }
}