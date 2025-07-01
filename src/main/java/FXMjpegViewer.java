package src.main.mjpeg;

import javax.swing.*;
import java.awt.*;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.image.BufferedImage;

public class FXMjpegViewer {
	private final ImageView imageView;

	
	public FXMjpegViewer(ImageView imageView)
	{
		this.imageView = imageView;
	}

	public void setImage(BufferedImage bufferedImage)
	{
		Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
		Platform.runLater(() -> imageView.setImage(fxImage));
	}

	    
}