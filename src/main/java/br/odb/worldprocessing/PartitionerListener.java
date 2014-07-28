package br.odb.worldprocessing;

public interface PartitionerListener {
	void onStatusUpdate(int pass, float state);

	void debugMessage(String msg);

	void warnMessage(String msg);

	void errorMessage(String msg);
}
