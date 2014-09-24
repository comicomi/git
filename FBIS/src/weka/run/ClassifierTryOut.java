package weka.run;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import weka.Classification;
import weka.classifier.NBClassifier;
import weka.classifiers.Classifier;

public class ClassifierTryOut {

	public static void main(String[] args) {
		ClassifierTryOut tryOut=new ClassifierTryOut();
		try {
			Classification cl=new Classification("trainingDatasetFB.arff");
		//	cl.optimize();
			cl.buildClassifierWithFiltersApplied(cl.buildDiscretizeFilter(), NBClassifier.createNBClassifier());
			cl.saveClassifier("classifier.model");
			
			tryOut.tryOutClassifier(cl.getClassifier(),"NB.txt");
			tryOut.tryOutClassifier(NBClassifier.createNBClassifier(),"NB.txt");
	/*		
				
			//NB			
			tryOut.tryOutClassifier(NBClassifier.createNBClassifier(),"NB.txt");
			tryOut.tryOutClassifier(NBClassifier.createNBClassifierWithKernelEstimator(),"NBKernel.txt");
			tryOut.tryOutClassifier(NBClassifier.createNBClassifierNormalized(),"NBNormalized.txt");
			tryOut.tryOutClassifier(NBClassifier.createNBClassifierNormalizedWithKernelEstimator(),"NBKernelNormalized.txt");
						
			tryOut.tryOutClassifierWithAttributeSelection(NBClassifier.createNBClassifier(),"NBSelection.txt");
			tryOut.tryOutClassifierWithAttributeSelection(NBClassifier.createNBClassifierWithKernelEstimator(),"NBKernelSelection.txt");
			tryOut.tryOutClassifierWithAttributeSelection(NBClassifier.createNBClassifierNormalized(),"NBNormalizedSelection.txt");
			tryOut.tryOutClassifierWithAttributeSelection(NBClassifier.createNBClassifierNormalizedWithKernelEstimator(),"NBKernelNormalizedSelection.txt");			
			
			//SVM
			tryOut.tryOutClassifier(SVMClassifier.defineSVMClassifierLin(),"SVMLin.txt");
			tryOut.tryOutClassifier(SVMClassifier.defineSVMClassifierPoly(),"SVMPoly.txt");
			tryOut.tryOutClassifier(SVMClassifier.defineSVMClassifierRBF(),"SVMRBF.txt");
			tryOut.tryOutClassifier(SVMClassifier.configureSVMClassifierLin(15),"SVMLinConf.txt");
			tryOut.tryOutClassifier(SVMClassifier.configureSVMClassifierPoly(15,1.5,5,0.3),"SVMPolyConf.txt");
			tryOut.tryOutClassifier(SVMClassifier.configureSVMClassifierRBF(15,1.2),"SVMRBFConf.txt");
						
			tryOut.tryOutClassifierWithAttributeSelection(SVMClassifier.defineSVMClassifierLin(),"SVMLinSelection.txt");
			tryOut.tryOutClassifierWithAttributeSelection(SVMClassifier.defineSVMClassifierPoly(),"SVMPolySelection.txt");
			tryOut.tryOutClassifierWithAttributeSelection(SVMClassifier.defineSVMClassifierRBF(),"SVMRBFSelection.txt");
			tryOut.tryOutClassifierWithAttributeSelection(SVMClassifier.configureSVMClassifierLin(15),"SVMLinConfSelection.txt");
			tryOut.tryOutClassifierWithAttributeSelection(SVMClassifier.configureSVMClassifierPoly(15,0.5,5,3),"SVMPolyConfSelection.txt");
			tryOut.tryOutClassifierWithAttributeSelection(SVMClassifier.configureSVMClassifierRBF(15,0.5),"SVMRBFConfSelection.txt");
			
		*/				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void tryOutClassifier(Classifier classifier, String fileName) throws Exception {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("classifierAnalysis\\"+fileName, true)));
		for (String option : classifier.getOptions()) {
			out.println(option);
		}		
		Classification classification=new Classification("trainingDatasetFB.arff");
		classification.buildClassifierWithFiltersApplied(classification.buildDiscretizeFilter(), classifier);
		classification.evaluateClassifier(classification.getClassifier(), out);
		out.close();
	}

	private void tryOutClassifierWithAttributeSelection(Classifier classifier, String fileName) throws Exception {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("classifierAnalysis\\"+fileName, true)));
		for (String option : classifier.getOptions()) {
			out.println(option);
		}		
		Classification classification=new Classification("trainingDatasetFB.arff");
		classification.buildClassifierWithFilteredAttributes(classifier);		
		classification.evaluateClassifier(classification.getClassifier(), out);
		out.close();
	}
	
	
	
}
