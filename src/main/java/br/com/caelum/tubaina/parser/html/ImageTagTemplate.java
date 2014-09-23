package br.com.caelum.tubaina.parser.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.util.HtmlSanitizer;

public class ImageTagTemplate {
	
	// TODO: make it work more gracefully... i.e., eliminate this workaround
	private static final String RELATIVEPATH = "$$RELATIVE$$/";
	private final Parser parser;

	public ImageTagTemplate(Parser parser) {
		this.parser = parser;
	}

	public String parse(ImageChunk chunk, boolean shouldUseHTMLWidth) {
		String path = chunk.getPath();
		String options = chunk.getOptions();  
		String imgsrc = FilenameUtils.getName(path);
		StringBuilder output = new StringBuilder("<img src=\"" + RELATIVEPATH + imgsrc + "\" ");

		Pattern label = Pattern.compile("(?s)(?i)label=(\\S+)%?");
		Matcher labelOptionMatcher = label.matcher(options);
		if (labelOptionMatcher.find()) {
			output.append("id=\"" + labelOptionMatcher.group(1) + "\" ");
		} else {
		    output.append("id=\"" + imgsrc + "\" ");
		}
		
		if (shouldUseHTMLWidth && getScale(options) != null) {
			output.append("width='" + getScale(options) + "%' ");
		}
		
		Pattern description = Pattern.compile("(?s)(?i)\"(.+?)\"");
		Matcher descriptionMatcher = description.matcher(options);
		// The image is resized when copied
		if (descriptionMatcher.find()) {
			String subtitle = new HtmlSanitizer().sanitize(descriptionMatcher.group(1));
			output.append("alt=\"" + subtitle + "\" />");
			subtitle = parser.parse(subtitle);
			output.append("\n<div><i>Fig. " + chunk.getChapterNumber() 
					+ "." + chunk.getImageNumber() + ": " +  subtitle +"</i></div><br><br>");
		} else {
			output.append("alt=\"" + imgsrc + "\" />");
			output.append("\n<div><i>Fig. " + chunk.getChapterNumber()
					+ "." + chunk.getImageNumber()+"</i></div><br><br>");
		}
		
		return output.toString();
	}
	
	public Integer getScale(final String options) {
		if (options == null) {
			return 100;
		}
		Pattern horizontalScale = Pattern.compile("(?s)(?i)w=(\\d+)%?");
		Matcher sMatcher = horizontalScale.matcher(options);

		if (sMatcher.find()) {
			return Integer.parseInt(sMatcher.group(1));
		}
		return 100;
	}
	
	public boolean shouldScale(final String options) {
	    if (options == null) {
	        return false;
	    }
        Pattern horizontalScale = Pattern.compile("(?s)(?i)w=(\\d+)%?");
        Matcher sMatcher = horizontalScale.matcher(options);
        
        return sMatcher.matches();
	}
}
