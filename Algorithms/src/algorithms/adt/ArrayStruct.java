package algorithms.adt;

public class ArrayStruct {

	String[] arr = new String[1];
	int posToFill = 0;

	public void push(String string) {
		arr[posToFill++] = string;
		if (posToFill == arr.length) {
			String[] array = new String[(2 * arr.length)];
			for (int i = 0; i < arr.length; i++) {
				array[i] = arr[i];
			}
			arr = array;
		}
		printSize();
	}

	public String pop() {
		String string = null;
		posToFill--;
		if (!isEmpty()) {
			string = arr[posToFill];
			arr[posToFill] = null;
			if (posToFill <= arr.length / 4) {
				int len = arr.length - (arr.length / 4);
				String[] array = new String[len];
				for (int i = 0; i < len; i++) {
					array[i] = arr[i];
				}
				arr = array;
			}
		} else {
			posToFill++;
		}
		printSize();
		return string;

	}

	private boolean isEmpty() {
		return posToFill == -1;
	}

	private void printSize() {
		System.out.println("size is[" + arr.length + "] posToFill[" + posToFill
				+ "]");
	}

	public void enqueue(String str) {
		push(str);
	}

	public String dequeue() {
		String str = null;
		posToFill --;
		if(!isEmpty()){
			str=arr[0];
			String[] array = new String[arr.length-1];
			for(int i=1;i<arr.length;i++){
				array[i-1]=arr[i];
			}
			arr=array;
		}else{
			posToFill++;
		}
		printSize();
		return str;
	}

}
