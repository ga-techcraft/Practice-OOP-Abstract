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

class Node{
  public int data;
  public Node next;

  public Node(int data){
    this.data = data;
    this.next = null;
  }
}


class IntegerLinkedList extends AbstractListInteger{
  public Node head;
  public int size = 0;

  public IntegerLinkedList(){}

  public IntegerLinkedList(int[] arr){
    super(arr);
    int[] initialData = this.getOriginalList();

    this.head = new Node(initialData[0]);
    Node tmp = this.head;
    for (int i = 1; i < initialData.length; i++) {
      tmp.next = new Node(initialData[i]);
      tmp = tmp.next;
    }

    this.size = arr.length;
  } 

  public int get(int position){
    if (position < 0 || position > this.size - 1) throw new IndexOutOfBoundsException("例外: getメソッドで指定されたインデックスの範囲が不正です。");

    Node tmp = this.head;
    for (int i = 0; i < position; i++) {
      tmp = tmp.next;
    }

    return tmp.data;
  }

  public void add(int element){
    Node tmp = this.head;
    while (tmp.next != null) {
      tmp = tmp.next;
    }
    tmp.next = new Node(element);

    this.size++;
  }

  public void add(int[] elements){
    Node tmp = this.head;
    while (tmp.next != null) {
      tmp = tmp.next;
    }

    for (int i = 0; i < elements.length; i++) {
      tmp.next = new Node(elements[i]);
      tmp = tmp.next;
    }

    this.size += elements.length;
  }

  public int pop(){
    Node tmp = this.head;
    
    // 削除するNodeの一つ前のNodeまで移動する
    for (int i = 0; i < this.size - 2; i++) {
      tmp = tmp.next;
    }
    
    int ret = tmp.next.data;
    tmp.next = null;
    this.size--;

    return ret;
  }

  public void addAt(int position, int element){
    // 範囲チェック
    if (position < 0 || position > this.size) throw new IndexOutOfBoundsException("例外: addAtメソッドで指定されたインデックスの範囲が不正です。");

    // 0のケース 先頭に追加するだけ
    if (position == 0) {
      Node newNode = new Node(element);
      newNode.next = this.head;
      this.head = newNode;
    }

    // this.sizeのケース 末尾に追加するだけ
    else if (position == this.size) {
      Node tmp = this.head;
      for (int i = 0; i < this.size - 1; i++) {
        tmp = tmp.next;
      }
      tmp.next = new Node(element);
    }

    // それ以外のケース
    else {
      // 追加したいインデックスの前まで移動する
      Node tmp = this.head;
      for (int i = 0; i < position - 1; i++) {
        tmp = tmp.next;
      }
      Node buff = tmp.next;
      tmp.next = new Node(element);
      tmp.next.next = buff;
    }

    // 共通処理
    this.size++;
  }

  // subList利用できそう
  public void addAt(int position, int[] elements){
    // position位置の確認
    if (position < 0 || position > this.size)  throw new IndexOutOfBoundsException("例外: addAtメソッドで指定されたインデックスの範囲が不正です。");

    // elementsをsubListの引数に渡して、新しい連結リストを作成
    IntegerLinkedList newLinkedList = new IntegerLinkedList(elements);

    // positionが0の場合、先頭に追加するだけ  
    if (position == 0) {
      Node tmp = newLinkedList.head;
      for (int i = 0; i < newLinkedList.size - 1; i++) {
        tmp = tmp.next;
      }
      tmp.next = this.head;
      this.head = newLinkedList.head;
      
      // positionがthis.sizeの場合、末尾に追加するだけ
    } else if (position == this.size) {
      Node tmp = this.head;
      for (int i = 0; i < this.size - 1; i++) {
        tmp = tmp.next;
      }
      tmp.next = newLinkedList.head;
    
      // それ以外の場合
    } else {
      Node tmp = this.head;
      for (int i = 0; i <= position - 2; i++) {
        tmp = tmp.next;
      }
      Node tmp2 = tmp.next;

      tmp.next = newLinkedList.head;

      Node tmp3 = newLinkedList.head;
      for (int i = 0; i < newLinkedList.size - 1; i++) {
        tmp3 = tmp3.next;
      }
      tmp3.next = tmp2;
    }
  }

  public int removeAt(int position){
    // 検証
    // 0 < position || position > this.size - 1 はNG
    if (0 > position || position > this.size - 1) throw new IndexOutOfBoundsException("例外: removeAtメソッドで指定されたインデックスの範囲が不正です。");

    // positionが0だったら
    // this.head = this.head.next;
    if (position == 0) {
      this.head = this.head.next;
    }

    // positionがthis.size - 1だったら
    // 末尾の前まで移動。そのnextをnullに
    else if (position == this.size - 1) {
      Node tmp = this.head;
      for (int i = 1; i < this.size - 1; i++) {
        tmp = tmp.next;
      }
      tmp.next = null;
    }

    // それ以外だったら
    // positionの前まで移動。その次の次の参照をいれる
    else {
      Node tmp = this.head;
      for (int i = 1; i <= position - 1; i ++) {
        tmp = tmp.next;
      }
      tmp.next = tmp.next.next;
    }

    return 0;
  }

  public void removeAllAt(int start){
    // インデックスの検証
    if (start < 0 || start > this.size - 1) throw new IndexOutOfBoundsException("例外: removeAllAtメソッドで指定されたインデックスの範囲が不正です。");

    if (start == 0) {
      this.head = null;
      return; 
    }

    // 削除したいインデックスの前まで移動し、その要素のnextをnullにする
    Node tmp = this.head;
    for (int i = 1; i <= start - 1; i++) {
      tmp = tmp.next;
    }
    tmp.next = null;

  }

  public void removeAllAt(int start, int end){
    // indexの検証
    if (start < 0 || start >= end || end > this.size - 1) throw new IndexOutOfBoundsException("例外: removeAllAtメソッドで指定されたインデックスの範囲が不正です。");

    // start == 0
    // endまで移動して、そのnextをheadにする
    if (start == 0) {
      for (int i = 1; i <= end + 1; i++) {
        this.head = this.head.next;
      }
    }

    // end == this.size - 1
    // startの前まで移動して、そのnextをnullにする
    else if (end == this.size - 1) {
      Node tmp = this.head;
      for (int i = 1; i <= start - 1; i++) {
        tmp = tmp.next;
      }
      tmp.next = null;
    }

    // 上記以外
    // startの前の要素のnextを、endの要素のnextにする
    else {
      Node tmp = this.head;
      for (int i = 1; i <= start - 1; i++) {
        tmp = tmp.next;
      }

      Node buffer = tmp;

      for (int i = start; i <= end; i++) {
        tmp = tmp.next;
      }

      buffer.next = tmp.next;
      
    }
  }

  public AbstractListInteger subList(int start){
    // start位置の確認
    if (start < 0 || start > this.size) throw new IndexOutOfBoundsException("例外: subListメソッドで指定されたインデックスの範囲が不正です。"); 

    int[] newData = new int[this.size - start];
    Node tmp = this.head;
    newData[0] = tmp.data;
    tmp = tmp.next; 
    for (int i = 1; i < newData.length; i++) {
      newData[i] = tmp.data;
      tmp = tmp.next;
    }

    return new IntegerLinkedList(newData);
  }

  public AbstractListInteger subList(int start, int end){
    // startとend位置の確認
    if (start < 0 || start >= end || end > this.size - 1) throw new IndexOutOfBoundsException("例外: subListメソッドで指定されたインデックスの範囲が不正です。"); 

    // newDataの作成
    int[] newData = new int[end - start + 1];

    // start位置まで移動
    Node tmp = this.head;

    for (int i = 0; i < newData.length; i++) {
      if (i >= start) {
        newData[i] = tmp.data;
      }
      tmp = tmp.next;
    }
  
    return new IntegerLinkedList(newData);
  }

  public void toArray(){
    if (this.head == null) {
      System.out.println(this.head);
      return;
    } 
    Node tmp = this.head;

    while(true){
      System.out.println(tmp.data);
      if (tmp.next == null) {
        break;
      }
      tmp = tmp.next;
    }
  }
}


class Main{

  public static void add(AbstractListInteger list, int element){
    list.add(element);
  }

  public static void main(String[] args){
    IntegerArrayList intArrayList = new IntegerArrayList(new int[]{1,2,3});
    IntegerLinkedList intLinkedList = new IntegerLinkedList(new int[]{1,2,3});

    
    try {
      
      // System.out.println(intArrayList.get(2));
      // System.out.println(intLinkedList.get(2));
      // intLinkedList.add(4);
      // intLinkedList.add(new int[]{5,6,7});
      // intLinkedList.pop();
      // intLinkedList.addAt(0, 0);
      // intLinkedList.addAt(7, 7);
      // intLinkedList.addAt(2, 0);

      // IntegerLinkedList newList1 = (IntegerLinkedList) intLinkedList.subList(-1, 1);
      // IntegerLinkedList newList2 = (IntegerLinkedList) intLinkedList.subList(1, 3);
      // IntegerLinkedList newList3 = (IntegerLinkedList) intLinkedList.subList(0, 1);
      // newList3.toArray();

      // intLinkedList.removeAllAt(0, 2); // 4,5,6になるはず
      // intLinkedList.removeAllAt(2, 5); // 1,2になるはず
      // intLinkedList.removeAllAt(2, 4); // 1,2,6になるはず
      add(intArrayList, 4);
      add(intLinkedList, 4);
      intArrayList.toArray();
      intLinkedList.toArray();

      // intLinkedList.toArray();
    } catch (Exception e) {
      System.out.println("例外: " + e.getMessage());
    }
  }
}