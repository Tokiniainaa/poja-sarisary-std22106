package hei.school.sarisary.endpoint.rest.controller.health;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

@RestController
public class BlackAndWhiteController {
    private Map<String, String> imageUrls = new HashMap<>(); // Map pour stocker les URLs des images

    @PutMapping("/black-and-white/{id}")
    public ResponseEntity<Void> convertToBlackAndWhite(@RequestBody byte[] imageBytes, @PathVariable String id) {
        try {
            byte[] blackAndWhiteBytes = convertToBlackAndWhite(imageBytes);
            // Stocker les bytes de l'image noir et blanc avec l'ID spécifié
            // Ici, vous pouvez stocker l'URL originale et l'URL transformée dans une map ou une base de données
            String originalUrl = "https://original.url"; // URL originale à remplacer par une URL réelle
            String transformedUrl = "https://transformed.url"; // URL transformée à remplacer par une URL réelle
            imageUrls.put(id, transformedUrl); // Stockage de l'URL transformée avec l'ID spécifié
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/black-and-white/{id}")
    public ResponseEntity<Map<String, String>> getBlackAndWhiteImageUrls(@PathVariable String id) {
        String transformedUrl = imageUrls.get(id);
        if (transformedUrl != null) {
            // Construire la réponse avec les URLs originale et transformée
            Map<String, String> urls = new HashMap<>();
            urls.put("original_url", "https://original.url"); // Remplacer par l'URL réelle
            urls.put("transformed_url", transformedUrl);
            return ResponseEntity.ok(urls);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public byte[] convertToBlackAndWhite(byte[] imageBytes) throws IOException {
        // Read image from byte array
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        // Convert image to black and white
        BufferedImage blackAndWhiteImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        blackAndWhiteImage.getGraphics().drawImage(image, 0, 0, null);
        // Write black and white image to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(blackAndWhiteImage, "png", baos);
        baos.flush();
        byte[] blackAndWhiteBytes = baos.toByteArray();
        baos.close();
        return blackAndWhiteBytes;
    }
}
