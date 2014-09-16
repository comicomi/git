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
import java.text.ParseException;
import java.util.Random;



import domen.UserData;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;

public class Classification {

	private String arffFilePath = System.getProperty("user.dir")+"\\data\\trainingDatasetFB.arff";
	private Instances dataset;
	private Classifier classifier;

	public Classification() {
		// TODO Auto-generated constructor stub
		try {
			File trainingFile=new File(arffFilePath);
			if(!trainingFile.exists()){
				createIntances();
			}
			else{
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
		InputStream stream = new FileInputStream(arffFilePath);
		DataSource loader = new DataSource(stream);
		dataset = loader.getDataSet();
		 if (dataset.classIndex() == -1)
		   dataset.setClassIndex(7);
	
	}

	public void createIntances() throws ParseException, IOException {

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

		FastVector timeValues= new FastVector(5);
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
		writeToTrainingArffFile(dataset);
	}

	public void addInstance(UserData userdata) throws IOException,
			ParseException {
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
		instance.setValue(dataset.attribute(7), userdata.getClassValue());
		if(dataset.checkInstance(instance)) dataset.add(instance);
		writeToTrainingArffFile(dataset);
	}

	private void writeToTrainingArffFile(Instances instances)
			throws IOException {
		Instances dataSet = instances;
		BufferedWriter writer = new BufferedWriter(new FileWriter(arffFilePath));
		writer.write(dataSet.toString());
		writer.flush();
		writer.close();
	}
	
	public void saveArffFile() throws IOException{
		ArffSaver saver = new ArffSaver(); 
		saver.setInstances(dataset); 
		saver.setFile(new File(arffFilePath)); 
		saver.writeBatch(); 
	}

	public void buildNBClassifier() throws Exception{
		classifier = new NaiveBayes();		
		classifier.buildClassifier(dataset);
	}
	
	public void buildSVMClassifier() throws Exception{
		classifier = new LibSVM();		
		classifier.buildClassifier(dataset);
	}
	
	
	public void saveClassifier(String classifierFileName) throws IOException{
		OutputStream os = new FileOutputStream(classifierFileName); 
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(os); 
		objectOutputStream.writeObject(classifier);
		objectOutputStream.close();
	}
	
	public void loadClassifier(String classifierFileName) throws ClassNotFoundException, IOException{
		InputStream is = new FileInputStream(classifierFileName); 
		ObjectInputStream objectInputStream = new ObjectInputStream(is);
		classifier = (Classifier) objectInputStream.readObject();
		objectInputStream.close(); 
		is.close();
	}
	
	public void evaluateClassifier() throws Exception{
		dataset = dataset.resample(new Random(42));
		double percent = 70.0; 
		int trainSize = (int) Math.round(dataset.numInstances() * percent / 100); 
		int testSize = dataset.numInstances() - trainSize;
		Instances train = new Instances(dataset, 0, trainSize); 
		Instances test = new Instances(dataset, trainSize, testSize);
		train.setClassIndex(7); test.setClassIndex(7);  
		Evaluation eval = new Evaluation(train); 
		eval.evaluateModel(classifier, test); 
		System.out.println(eval.toSummaryString());
		System.out.println(eval.weightedFMeasure()); 
		System.out.println(eval.weightedPrecision()); 
		System.out.println(eval.weightedRecall());		
	}
	
	public void classify() throws Exception{		
		System.out.println(classifier.classifyInstance(dataset.firstInstance())); 
		System.out.println(dataset.attribute(dataset.classIndex()).value((int)dataset.firstInstance().classValue()));
		System.out.println(classifier.distributionForInstance(dataset.firstInstance())); 
	}
	
}
