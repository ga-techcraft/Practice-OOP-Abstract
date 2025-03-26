import java.util.Arrays;

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

  public IntegerArrayList(){}

  public IntegerArrayList(int[] arr){
    super(arr);

    int[] initialData = this.getOriginalList();

    // 配列サイズがデフォルトサイズ（10）より大きかったら、引数の配列サイズの2倍のサイズの配列を作成する。
    if (initialData.length > this.size) {
      this.data = new int[initialData.length * 2];
      this.size = data.length;
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

    // 共通の処理
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

    // 共通処理
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
    // positionが配列のサイズ内であり、かつ値が連続する位置かどうか確認
    if (position < 0 || this.size - 1 < position || this.pos + 1 < position) {
      throw new IndexOutOfBoundsException("例外: addAtメソッドで指定されたインデックスの範囲が不正です。");
    }

    // 現在の配列に空きがなかったら、配列サイズ２倍にする
    if (this.size - 1 == this.pos) {
      int[] newData = new int[this.size * 2];

      for (int i = 0; i <= this.pos; i++) {
        newData[i] = this.data[i];
      }

      this.data = newData;
    }
    
    // posの次に追加する場合はO(1)で追加可能
    if (position == this.pos + 1) {
      this.data[this.pos + 1] = element;
    // データの移動が必要な場合はO(n)で追加可能
    } else {
      int[] buffer = new int[this.pos - position + 1];
      for (int i = 0; i < buffer.length; i++) {
        buffer[i] = this.data[position + i];
      }

      this.data[position] = element;
      
      for (int i = 0; i < buffer.length; i++) {  
        this.data[position + 1 + i] = buffer[i];
      } 

    }

    // 共通の処理
    this.pos += 1;
  }

  public void addAt(int position, int[] elements){
    // positionが配列のサイズ内であり、かつ値が連続する位置かどうか確認
    if (position < 0 || this.size - 1 < position || this.pos + 1 < position) {
      throw new IndexOutOfBoundsException("例外: addAtメソッドで指定されたインデックスの範囲が不正です。");
    }

    // 現在の配列に空きがなかったら、引数の配列サイズ分を追加する
    if (this.size - (this.pos + 1) < elements.length) {
      int[] newData = new int[this.size + elements.length];

      for (int i = 0; i <= this.pos; i++) {
        newData[i] = this.data[i];
      }

      this.data = newData;
    }

    // posの次からデータを追加する
    if (position == this.pos + 1) {
      for (int i = 0; i < elements.length; i++) {
        this.data[position + i] = elements[i];
      }
    } else {
      // 途中でデータを追加して、既存データを移動させる
      // 指定されたposition以降のデータをバッファーに追加する
      int[] buffer = new int[this.pos - position + 1];
      for (int i = 0; i < buffer.length; i++) {
        buffer[i] = this.data[position + i];
      }

      // 新規データを入れ込む
      for (int i = 0; i < elements.length; i++) {
        this.data[position + i] = elements[i];
      }

      // 続けて既存データを入れ込む
      for (int i = 0; i < buffer.length; i++) {
        this.data[position + elements.length + i] = buffer[i];
      }

    }

    // 共通処理
    this.pos += elements.length;

  }

  public int removeAt(int position){
    if (position < 0 || position > this.pos) {
      throw new IndexOutOfBoundsException("例外: removeAtメソッドで指定されたインデックスの範囲が不正です。");
    }

    if (position != this.pos) {
      int[] buffer = new int[this.pos - position];

      for (int i = 0; i < buffer.length; i++) {
        this.data[position + i] = this.data[position + 1 + i];
      }
    }

    this.pos -= 1;
    
    return 0;
  }

  public void removeAllAt(int start){
    if (start < 0 || start > this.pos) {
      throw new IndexOutOfBoundsException("例外: removeAllAtメソッドで指定されたインデックスの範囲が不正です。");
    }

    this.pos = start - 1;
  }

  public void removeAllAt(int start, int end){
    if (start < 0 || start >= end || end > this.pos) {
      throw new IndexOutOfBoundsException("例外: removeAllAtメソッドで指定されたインデックスの範囲が不正です。");
    }

    if (start == 0 && end < this.pos) { 
      for (int i = 0; i < this.pos - end; i++) {
        this.data[i] = this.data[end + 1 + i];
      }

    } else if (start > 0 && end == this.pos) {
      this.pos = start - 1;
      return;
    } else {
      for (int i = 0; i < this.pos - end; i++) {
        this.data[start + i] = this.data[end + 1 + i];
      }
    }

    this.pos = this.pos - end - 1;
  }

  public AbstractListInteger subList(int start){
    if (start < 0 || start >= this.pos) {
      throw new IndexOutOfBoundsException("例外: subListメソッドで指定されたインデックスの範囲が不正です。");
    }

    int[] newData = new int[this.pos - start + 1];
    for (int i = 0; i < newData.length; i++) {
      newData[i] = this.data[start + i];
    }

    return new IntegerArrayList(newData);
  }

  public AbstractListInteger subList(int start, int end){
    if (start < 0 || start >= end || end > this.pos) {
      throw new IndexOutOfBoundsException("例外: subListメソッドで指定されたインデックスの範囲が不正です。");
    }

    int[] newData = new int[end - start + 1];
    for (int i = 0; i < newData.length; i++) {
      newData[i] = this.data[start + i];
    }
    
    return new IntegerArrayList(newData);
  }

  public void toArray(){
    int[] buffer = new int[this.pos + 1];
    for (int i = 0; i <= this.pos; i++) {
      buffer[i] = this.data[i];
    }
    
    System.out.println(Arrays.toString(buffer));
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
    IntegerArrayList intArrList = new IntegerArrayList(new int[]{1,2,3,4,5,6,7,8,9,10});

    try {
      
      intArrList.add(9);
      intArrList.pop();
      intArrList.addAt(10, 11);
      intArrList.addAt(11, new int[]{12,13,14});
      intArrList.add(15);
      intArrList.add(new int[]{16,17});
      intArrList.removeAllAt(15);
      intArrList.removeAt(14);
      intArrList.removeAllAt(0, 2);
      intArrList.addAt(0, new int[]{1,2,3});

      intArrList.toArray();

      // AbstractListInteger newIntArrList = intArrList.subList(0);
      // int[] initialList = newIntArrList.getOriginalList();
      // System.out.println(Arrays.toString(initialList));

      AbstractListInteger newIntArrList = intArrList.subList(0, 13);
      int[] initialList = newIntArrList.getOriginalList();
      System.out.println(Arrays.toString(initialList));

    
    } catch (Exception e) {
      System.out.println("例外: " + e.getMessage());
    }
  }
}