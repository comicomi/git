package weka.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.core.SelectedTag;

public class SVMClassifier {

	public static Classifier defineSVMClassifierLin() throws Exception {
		LibSVM classifier = new LibSVM();
		classifier.setSVMType(new SelectedTag(LibSVM.SVMTYPE_C_SVC,
				LibSVM.TAGS_SVMTYPE));
		classifier.setKernelType(new SelectedTag(LibSVM.KERNELTYPE_LINEAR,
				LibSVM.TAGS_KERNELTYPE));
		classifier.setCost(10);
		classifier.setNormalize(true);
		classifier.setProbabilityEstimates(true);
		return classifier;
	}

	public static Classifier configureSVMClassifierLin(int cost)
			throws Exception {
		LibSVM classifier = new LibSVM();
		classifier.setSVMType(new SelectedTag(LibSVM.SVMTYPE_C_SVC,
				LibSVM.TAGS_SVMTYPE));
		classifier.setKernelType(new SelectedTag(LibSVM.KERNELTYPE_LINEAR,
				LibSVM.TAGS_KERNELTYPE));
		classifier.setCost(cost);
		classifier.setNormalize(true);
		classifier.setProbabilityEstimates(true);
		return classifier;
	}

	public static Classifier defineSVMClassifierPoly() throws Exception {
		LibSVM classifier = new LibSVM();
		classifier.setSVMType(new SelectedTag(LibSVM.SVMTYPE_C_SVC,
				LibSVM.TAGS_SVMTYPE));
		classifier.setKernelType(new SelectedTag(LibSVM.KERNELTYPE_POLYNOMIAL,
				LibSVM.TAGS_KERNELTYPE));
		classifier.setDegree(3);
		classifier.setGamma(0.8);
		classifier.setCost(10);
		classifier.setCoef0(0.5);
		classifier.setNormalize(true);
		classifier.setProbabilityEstimates(true);
		return classifier;
	}

	public static Classifier configureSVMClassifierPoly(double cost,
			double gamma, int degree, double coef0) throws Exception {
		LibSVM classifier = new LibSVM();
		classifier.setSVMType(new SelectedTag(LibSVM.SVMTYPE_C_SVC,
				LibSVM.TAGS_SVMTYPE));
		classifier.setKernelType(new SelectedTag(LibSVM.KERNELTYPE_POLYNOMIAL,
				LibSVM.TAGS_KERNELTYPE));
		classifier.setDegree(degree);
		classifier.setGamma(gamma);
		classifier.setCoef0(coef0);
		classifier.setCost(cost);
		classifier.setNormalize(true);
		classifier.setProbabilityEstimates(true);
		return classifier;
	}

	public static Classifier defineSVMClassifierRBF() throws Exception {
		LibSVM classifier = new LibSVM();
		classifier.setSVMType(new SelectedTag(LibSVM.SVMTYPE_C_SVC,
				LibSVM.TAGS_SVMTYPE));
		classifier.setKernelType(new SelectedTag(LibSVM.KERNELTYPE_RBF,
				LibSVM.TAGS_KERNELTYPE));
		classifier.setNormalize(true);
		classifier.setGamma(0.7);
		classifier.setCost(10);
		classifier.setProbabilityEstimates(true);
		return classifier;
	}

	public static Classifier configureSVMClassifierRBF(double cost, double gamma)
			throws Exception {
		LibSVM classifier = new LibSVM();
		classifier.setSVMType(new SelectedTag(LibSVM.SVMTYPE_C_SVC,
				LibSVM.TAGS_SVMTYPE));
		classifier.setKernelType(new SelectedTag(LibSVM.KERNELTYPE_RBF,
				LibSVM.TAGS_KERNELTYPE));
		classifier.setNu(0.48);
		classifier.setNormalize(true);
		classifier.setGamma(gamma);
		classifier.setCost(cost);
		classifier.setProbabilityEstimates(true);
		return classifier;
	}

}
