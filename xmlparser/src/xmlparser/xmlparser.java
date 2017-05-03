package xmlparser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;

public class xmlparser {
	private static String directory = "./res";
	private Set<String> types = new TreeSet<>();

	public boolean parser(String path) {
		if (path == null || path.isEmpty()) {
			// use default path.
			path = directory;
		}
		File appFolder = new File(path);
        if (appFolder.isDirectory()) {
            File[] xmlFiles = appFolder.listFiles(new XmlFileFilter());

            for (File xmlFile : xmlFiles) {
            	parseXmlFile(xmlFile, path);
            }
            writeUniqueType(path);
        }
		return true;
	}

	private void writeUniqueType(String path) {
		try (FileOutputStream fos = new FileOutputStream(path + "/UniqueOut.txt", true);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
				BufferedWriter bw = new BufferedWriter(osw);) {
			for (String type : types) {
				bw.write(type);
				bw.newLine();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void parseXmlFile(File xmlFile, String path) {
		SAXReader reader = new SAXReader();
		try (FileOutputStream fos = new FileOutputStream(path + "/out.txt", true);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
				BufferedWriter bw = new BufferedWriter(osw);){
			Document doc = reader.read(xmlFile);
			String appName = ((Element) doc.selectNodes("/xpath[@name]").get(0)).attributeValue("name");
			bw.write(appName);
			bw.newLine();
			List<Element> slots = doc.selectNodes("xpath");
			for (Element slot : slots) {
				String type = slot.attributeValue("type") + " + " + slot.attributeValue("subtype");
				types.add(type);
				bw.write(type);
				bw.newLine();
			}
			bw.newLine();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			System.err.println("parse " + xmlFile + " failed.");
		}
	}

	public static void main(String[] args) {
		new xmlparser().parser("");
	}
}

class XmlFileFilter implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        if (pathname.isFile()) {
            return pathname.toString().endsWith(".xml");
        }
        return false;
    }
}
