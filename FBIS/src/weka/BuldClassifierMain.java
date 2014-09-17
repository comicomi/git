package weka;

import java.io.IOException;
import java.text.ParseException;

import weka.classifiers.Classifier;

public class BuldClassifierMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Classification classification=new Classification();
		try {
			System.out.println(System.getProperty("user.dir"));
			classification.buildNBClassifier();;
			classification.saveClassifier(System.getProperty("user.dir")+"\\data\\"+"NBKlasifikator.model");
			classification.loadClassifier(System.getProperty("user.dir")+"\\data\\"+"NBKlasifikator.model");
		    classification.classify();
			classification.evaluateClassifier();
			classification.buildSVMClassifier();
			classification.saveClassifier(System.getProperty("user.dir")+"\\data\\"+"SVMKlasifikator.model");
			classification.loadClassifier(System.getProperty("user.dir")+"\\data\\"+"SVMKlasifikator.model");
			classification.classify();
			classification.evaluateClassifier();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
