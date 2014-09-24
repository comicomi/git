package weka;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Random;

import weka.attributeSelection.BestFirst;
import weka.attributeSelection.ClassifierSubsetEval;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.Discretize;
import domen.UserData;

public class Classification {

	private String arffFilePath = "trainingDatasetFB.arff";
	private String classifierFilePath = "classifier.model";
	private String dataFolderPath = "data";
	private Instances dataset;
	private Classifier finalClassifier;

	public Classification(String arffFile) {
		// TODO Auto-generated constructor stub
		try {
			if(arffFile==null) {
				createIntances(false);
				return;
			}
			arffFilePath = arffFile;
			File dataFolder = new File(dataFolderPath);
			if (!dataFolder.exists())
				dataFolder.mkdir();
			File trainingFile = new File(dataFolderPath + "\\" + arffFilePath);
			if (!trainingFile.exists()) {
				createIntances(true);
			} else {
				loadDataset();
			}
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

	private void loadDataset() throws Exception {
		InputStream stream = new FileInputStream(dataFolderPath + "\\"
				+ arffFilePath);
		DataSource loader = new DataSource(stream);
		dataset = loader.getDataSet();
		if (dataset.classIndex() == -1)
			dataset.setClassIndex(dataset.numAttributes() - 1);

	}

	public void createIntances(boolean train) throws ParseException, IOException {

		Attribute friendsNumber = new Attribute("friendsNumber");
		Attribute averageNumberOfLikes = new Attribute("averageNumberOfLikes");
		Attribute tagsNumber = new Attribute("tagsNumber");

		FastVector isNewValues = new FastVector(2);
		isNewValues.addElement("yes");
		isNewValues.addElement("no");
		Attribute isNew = new Attribute("isNew", isNewValues);

		FastVector genderValues = new FastVector(2);
		genderValues.addElement("male");
		genderValues.addElement("female");
		Attribute gender = new Attribute("gender", genderValues);

		FastVector ageRangeValues = new FastVector(4);
		ageRangeValues.addElement("teenagers");
		ageRangeValues.addElement("youngAdults");
		ageRangeValues.addElement("adults");
		ageRangeValues.addElement("seriousAdults");
		Attribute ageRange = new Attribute("ageRange", ageRangeValues);

		FastVector timeValues = new FastVector(5);
		timeValues.addElement("morning");
		timeValues.addElement("midday");
		timeValues.addElement("afternoon");
		timeValues.addElement("night");
		timeValues.addElement("lateNight");
		Attribute time = new Attribute("time", timeValues);

		FastVector noticedValues = new FastVector(2);
		noticedValues.addElement("noticed");
		noticedValues.addElement("unnoticed");
		Attribute classes = new Attribute("noticed", noticedValues);

		FastVector attributes = new FastVector();
		attributes.addElement(friendsNumber);
		attributes.addElement(averageNumberOfLikes);
		attributes.addElement(tagsNumber);
		attributes.addElement(isNew);
		attributes.addElement(gender);
		attributes.addElement(ageRange);
		attributes.addElement(time);
		attributes.addElement(classes);

		dataset = new Instances("profilePictureLikes", attributes, 0);
		dataset.setClassIndex(7);
		if(train) writeToArffFile(dataset);
	}

	public void addInstance(UserData userdata, boolean train)
			throws IOException, ParseException {
		Instance instance = new Instance(8);
		instance.setDataset(dataset);
		instance.setValue(dataset.attribute(0), userdata.getFriendsNumber());
		instance.setValue(dataset.attribute(1),
				userdata.getAverageNumberOfLikes());
		instance.setValue(dataset.attribute(2), userdata.getTagsNumber());
		instance.setValue(dataset.attribute(3), userdata.isNew());
		instance.setValue(dataset.attribute(4), userdata.getGender());
		instance.setValue(dataset.attribute(5), userdata.getAgeRange());
		System.out.println(userdata.getDateTimeCreated());
		instance.setValue(dataset.attribute(6), userdata.getTime());
		if (train)
			instance.setValue(dataset.attribute(7), userdata.getClassValue());
		if (dataset.checkInstance(instance))
			dataset.add(instance);
		if(train) writeToArffFile(dataset);
	}

	private void writeToArffFile(Instances instances)
			throws IOException {
		File dataFolder = new File("data");
		if (!dataFolder.exists())
			dataFolder.mkdir();
		Instances dataSet = instances;
		BufferedWriter writer = new BufferedWriter(new FileWriter(
				dataFolderPath + "\\" + arffFilePath));

		writer.write(dataSet.toString());
		writer.flush();
		writer.close();
	}

	public void saveClassifier(String classifierFileName) throws IOException {
		File dataFolder = new File(dataFolderPath);
		if (!dataFolder.exists())
			dataFolder.mkdir();
		File file = new File(dataFolderPath+"\\"+classifierFileName);
		file.createNewFile();
		OutputStream os = new FileOutputStream(dataFolderPath + "\\"
				+ classifierFileName);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
		objectOutputStream.writeObject(finalClassifier);
		objectOutputStream.close();
	}

	public void loadClassifier(String classifierFileName)
			throws ClassNotFoundException, IOException {
		InputStream is = new FileInputStream(dataFolderPath + "\\"
				+ classifierFileName);
		ObjectInputStream objectInputStream = new ObjectInputStream(is);
		finalClassifier = (Classifier) objectInputStream.readObject();
		objectInputStream.close();
		is.close();
	}

	public void evaluateClassifier(Classifier classifier, PrintWriter out)
			throws Exception {
		Evaluation eval = new Evaluation(dataset);
		eval.crossValidateModel(classifier, dataset, 10, new Random(1));
		out.println(eval.toSummaryString());
		out.println(eval.toClassDetailsString());
		out.println(eval.toMatrixString());
		out.println("Weighted F-measure: " + eval.weightedFMeasure());
		out.println("Precision: " + eval.weightedPrecision());
		out.println("Recall: " + eval.weightedRecall());
		out.println("Accuracy: " + eval.pctCorrect());
	}

	public double classify() throws Exception {
		loadClassifier(classifierFilePath);
		double predicted = finalClassifier.classifyInstance(dataset
				.firstInstance());
		System.out.println(predicted);
		System.out.println(dataset.attribute(dataset.classIndex()).value(
				(int) predicted));
		return finalClassifier.classifyInstance(dataset.firstInstance());
	}

	public Discretize buildDiscretizeFilter() throws Exception {
		Discretize discretizeFilter = new Discretize();
		discretizeFilter.setAttributeIndices("first-last");
		discretizeFilter.setInputFormat(dataset);
		discretizeFilter.setBins(10);
		discretizeFilter.setUseEqualFrequency(true);
		return discretizeFilter;
	}

	public void buildClassifierWithFiltersApplied(Filter filter,
			Classifier classifier) throws Exception {
		FilteredClassifier filteredClassifier = new FilteredClassifier();
		filteredClassifier.setClassifier(classifier);
		filteredClassifier.setFilter(filter);
		filteredClassifier.buildClassifier(dataset);
		finalClassifier = filteredClassifier;
	}

	public AttributeSelection buildAttributSelectionFilter(Classifier classifier)
			throws Exception {
		AttributeSelection asFilter = new AttributeSelection();
		ClassifierSubsetEval evaluator = new ClassifierSubsetEval();
		evaluator.setClassifier(classifier);
		asFilter.setEvaluator(evaluator);
		asFilter.setSearch(new BestFirst());
		return asFilter;
	}

	public void buildClassifierWithFilteredAttributes(Classifier classifier)
			throws Exception {
		Filter[] filters = new Filter[2];
		filters[0] = buildDiscretizeFilter();
		filters[1] = buildAttributSelectionFilter(classifier);
		MultiFilter multiFilter = new MultiFilter();
		multiFilter.setFilters(filters);
		buildClassifierWithFiltersApplied(multiFilter, classifier);
	}

	public Classifier getClassifier() {
		return finalClassifier;
	}

	public void setClassifier(Classifier classifier) {
		this.finalClassifier = classifier;
	}

	//proba
	public void optimize() throws Exception {
		CVParameterSelection ps = new CVParameterSelection();
		ps.setClassifier(new LibSVM());
		ps.setNumFolds(5); // using 5-fold CV
		ps.addCVParameter("C 0.1 0.5 5");

		// build and output best options
		ps.buildClassifier(dataset);
		System.out.println(Utils.joinOptions(ps.getBestClassifierOptions()));
	}
}
