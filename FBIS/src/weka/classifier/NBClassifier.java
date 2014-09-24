package weka.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;

public class NBClassifier {

	public static Classifier createNBClassifier() throws Exception {
		NaiveBayes classifier = new NaiveBayes();
		return classifier;
	}

	public static Classifier createNBClassifierNormalized() throws Exception {
		NaiveBayes classifier = new NaiveBayes();
		classifier.setUseSupervisedDiscretization(true);
		return classifier;
	}

	public static Classifier createNBClassifierNormalizedWithKernelEstimator()
			throws Exception {
		NaiveBayes classifier = new NaiveBayes();
		classifier.setUseSupervisedDiscretization(true);
		classifier.setUseKernelEstimator(true);
		return classifier;
	}
	
	public static Classifier createNBClassifierWithKernelEstimator() throws Exception {
		NaiveBayes classifier = new NaiveBayes();
		classifier.setUseKernelEstimator(true);
		return classifier;
	}

}
