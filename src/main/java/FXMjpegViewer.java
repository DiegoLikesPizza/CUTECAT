package src.main.mjpeg;

import javax.swing.*;
import java.awt.*;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

public class FXMjpegViewer {
	private final List<ImageView> imageViews = new CopyOnWriteArrayList<>();

	
	public FXMjpegViewer(ImageView... views) {
		for (ImageView view : views) {
			imageViews.add(view);
		}
	}

	public void addImageView(ImageView view) {
		imageViews.add(view);
	}

	public void onFrame(Image image)
	{
		Platform.runLater(() -> {
			for (ImageView view : imageViews) {
				view.setImage(image);
			}
		});
	}

	    
}