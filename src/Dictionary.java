import java.util.Locale;

public class Dictionary {
    private int num;
    private int max;
    private WordInfo[] dict;

    public Dictionary() {
        num = 0;
        max = 1500;
        dict = new WordInfo[max];
    }

    // Search for the target word (binary search)
    public int binarySearch(String targetWord){
        int lo = 0, hi = num - 1, mid;
        while (lo <= hi) {
            mid = (lo + hi) / 2;
            if (dict[mid].word.equals(targetWord)) return mid;
            if (dict[mid].word.compareTo(targetWord) > 0) hi = mid - 1;
            if (dict[mid].word.compareTo(targetWord) < 0) lo = mid + 1;
        }
        return -1;
    }

    // Merge Sort
    public void merge(WordInfo[] dict, int lo, int mid, int hi){
        int n1 = mid - lo + 1;
        int n2 = hi - mid;

        // temp arrays
        WordInfo[] mergeLeft = new WordInfo[n1];
        WordInfo[] mergeRight = new WordInfo[n2];

        // Copy data to temp arrays
        for (int i=0;i<n1;i++){
            mergeLeft[i] = dict[lo+i];
        }
        for (int j=0;j<n2;j++){
            mergeRight[j] = dict[mid+1+j];
        }

        // initial index for 1st and 2nd sub-arrays
        int x=0, y=0;

        // initial index of merged sub-array
        int z = lo;
        while(x<n1 && y<n2){
            if(mergeLeft[x].word.compareTo(mergeRight[y].word) <= 0){
                dict[z] = mergeLeft[x];
                x++;
            }
            else {
                dict[z] = mergeRight[y];
                y++;
            }
            z++;
        }

        // Copy remaining elements of mergeLeft
        while(x<n1 && mergeLeft[x] != null){
            dict[z] = mergeLeft[x];
            x++;
            z++;
        }
        // Copy remaining elements of mergeRight
        while(y<n2 && mergeRight[y] != null){
            dict[z] = mergeRight[y];
            y++;
            z++;
        }
    }

    public void mergeSort(WordInfo[] dict, int lo, int hi){
        if (lo<hi){
            int mid = (lo + hi) / 2;
            mergeSort(dict,lo,mid);
            mergeSort(dict,mid+1,hi);
            merge(dict,lo,mid,hi);
        }

    }

    // For adding new words from wordList.txt only
    public boolean add(String word){
        if (num<max){  // check if num of words stored < dict max
            if (binarySearch(word) == -1){
                String tempMean = "Undefined word";
                dict[num] = new WordInfo(word.toLowerCase(),tempMean);
                num++;
                mergeSort(dict,0, num-1);
                return true;
            }
            return false;
        }
        return false;
    }

    // For adding new words from program
    public boolean add(String word, String meaning){
        if (num < max){  // check if num of words stored < dict max
            if (binarySearch(word) == -1) {
                dict[num] = new WordInfo(word.toLowerCase(), meaning);
                num++;
                mergeSort(dict,0, num-1);
                return true;
            }
            return false;
        }
        return false;
    }

    // For deleting word
    public boolean delete(String word){
        int key = binarySearch(word.toLowerCase());
        if (key != -1){
            for (int i=key; i<num-1;i++){
                dict[i] = dict[i+1];
            }
            num--;
            return true;
        }
        return false;
    }

    // Check word exists
    public boolean exists(String word){
        word = word.toLowerCase();
        int key = binarySearch(word);
        return key != -1;  // if word exists -> True; if not exists -> False;
    }

    // Get meaning of word
    public String getMeaning(String word){
        int key = binarySearch(word.toLowerCase());
        if (key != -1){
            return dict[key].meaning;
        }
        return "N/A";
    }

    // Count total number of words in dictionary
    public int getCount(){
        return num;
    }

    // Print all words only
    public String printWordList(){
        String wordlist ="";
        for (int i=0; i< num;i++){
            wordlist += dict[i].word + "\n";
        }
        return wordlist;
    }

    // Print all words + meanings
    public void printDictionary(){
        for (int i=0;i<num;i++){
            System.out.println(dict[i].toString());
        }
    }
}
