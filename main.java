abstract class AbstractListInteger{
  private int[] initialList;

  public AbstractListInteger(){
      this.initialList = new int[]{};
  }

  public AbstractListInteger(int[] arr){
      this.initialList = arr;
  }

  public int[] getOriginalList(){
      return initialList;
  }
  
  // AbstractListIntegerが実装しなければならない抽象メソッド
  public abstract int get(int position); // 特定位置の要素を取得します。
  public abstract void add(int element); // リストの最後に追加します。
  public abstract void add(int[] elements); // リストの最後の要素に追加します。
  public abstract int pop();// リストの最後から削除します。削除した要素を返します。
  public abstract void addAt(int position, int element);// 指定された位置に要素を追加します。
  public abstract void addAt(int position, int[] elements);// 指定された位置に複数の要素を追加します。
  public abstract int removeAt(int position);// 指定した位置にある要素を削除します。削除した要素を返します。
  public abstract void removeAllAt(int start);// 指定された位置から始まるすべての要素を削除します。
  public abstract void removeAllAt(int start, int end);// startからendまでの全ての要素を削除します。
  public abstract AbstractListInteger subList(int start); // AbstractListIntegerの部分リストを、指定された位置から最後まで返します。
  public abstract AbstractListInteger subList(int start, int end); // startからendまでのAbstractListIntegerの部分リストを返します。
}

class IntegerArrayList extends AbstractListInteger{
  private int[] data = new int[10]; 
  private int size = data.length; // 配列サイズ
  private int pos = -1; // 値が入っている最後のインデックス。何も入っていない場合は-1。

  public IntegerArrayList(){
    super();
  }

  public IntegerArrayList(int[] arr){
    super(arr);

    int[] initialData = this.getOriginalList();

    // 配列サイズがデフォルトサイズ（10）より大きかったら、引数の配列サイズの2倍のサイズの配列を作成する。
    if (initialData.length > this.size) {
      this.data = new int[initialData.length * 2];
      this.size = data.length;
      for (int i = 0; i < initialData.length; i++) {
        this.data[i] = initialData[i];
      }
    } 
      
    for (int i = 0; i < initialData.length; i++) {
        this.data[i] = initialData[i];
    }

    this.pos += arr.length;
  }

  public int get(int position){
    if (position < 0 || position > this.pos) {
      throw new IndexOutOfBoundsException("getメソッドの引数が不正です。");
    }

    return this.data[position];
  }

  public void add(int element){
    // 配列の空きがない場合、配列サイズを2倍にする
    if (this.size == this.pos + 1) {
      int[] newData = new int[this.size * 2];

      // 既存のデータを入れる
      for (int i = 0; i <= this.pos; i++) {
        newData[i] = this.data[i];
      }
      newData[this.pos + 1] = element;

      this.data = newData;
      this.size = data.length;
    } else {
      this.data[this.pos + 1] = element;
    }
    this.pos += 1;
  }

  public void add(int[] elements){
    // 配列の空きがない場合、現在の配列サイズに引数の配列サイズを＋する。
    if (this.size - (this.pos + 1) < elements.length) {
      int[] newData = new int[this.size + elements.length];

      // 既存データを入れる
      for (int i = 0; i <= this.pos; i++) {
        newData[i] = this.data[i];
      }

      // 新規データを入れる
      for (int i = 0; i < elements.length; i++) {
        newData[pos + 1 + i] = elements[i];
      }

      this.data = newData;

    // 以下では既存の配列に新規データを入れる
    } else {
      // 新規データを入れる
      for (int i = 0; i < elements.length; i++) {
        this.data[pos + 1 + i] = elements[i];
      }
    }

    this.pos += elements.length;
  }

  public int pop() {
    // 配列にデータがない場合は例外を投げる
    if (this.pos == -1) {
      throw new IllegalStateException("例外: データが無いのでpopメソッドは使えません。");
    }

    // posのインデックスの値を削除して、posを-1する
    int buffer = this.data[this.pos];
    this.data[this.pos] = 0;
    this.pos -= 1;

    return buffer;
  }

  public void addAt(int position, int element){
    // 
  }

  public void addAt(int position, int[] elements){

  }

  public int removeAt(int position){
    return 0;
  }

  public void removeAllAt(int start){

  }

  public void removeAllAt(int start, int end){

  }

  public AbstractListInteger subList(int start){
    return this;
  }

  public AbstractListInteger subList(int start, int end){
    return this;
  }
}


class IntegerLinkedList extends AbstractListInteger{
  public int get(int position){
    return 0;
  }

  public void add(int element){

  }

  public void add(int[] elements){

  }

  public int pop(){
    return 0;
  }

  public void addAt(int position, int element){

  }

  public void addAt(int position, int[] elements){

  }

  public int removeAt(int position){
    return 0;
  }

  public void removeAllAt(int start){

  }

  public void removeAllAt(int start, int end){

  }

  public AbstractListInteger subList(int start){
    return this;
  }

  public AbstractListInteger subList(int start, int end){
    return this;
  }
}

class Main{
  public static void main(String[] args){
    IntegerArrayList intArrList = new IntegerArrayList(new int[]{1,2,3,4,5,6,7});

    try {
      // intArrList.add(8);
      // intArrList.add(12);
      // intArrList.add(13);

      intArrList.add(new int[]{11, 12, 13, 14});
      intArrList.pop();
      System.out.println(intArrList.get(10));
    } catch (Exception e) {
      System.out.println("例外: " + e.getMessage());
    }
  }
}
