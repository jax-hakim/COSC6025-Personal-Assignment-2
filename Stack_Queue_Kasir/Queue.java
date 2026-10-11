/**
 * Queue (antrean) berbasis linked list dengan prinsip FIFO.
 * enqueue di belakang (tail), dequeue dari depan (head). Keduanya O(1).
 * Kapasitas dibatasi MAKS (<= 5 pelanggan sesuai panduan dosen).
 */
public class Queue {
    public static final int MAKS = 5;

    private Node front; // pelanggan paling depan (dilayani lebih dulu)
    private Node rear;  // pelanggan paling belakang
    private int size;

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public boolean isFull() {
        return size >= MAKS;
    }

    /** Cek apakah nomor antrean sudah ada di antrean. */
    public boolean contains(String nomorAntrian) {
        for (Node n = front; n != null; n = n.next) {
            if (n.nomorAntrian.equalsIgnoreCase(nomorAntrian)) {
                return true;
            }
        }
        return false;
    }

    /** Masukkan pelanggan di belakang antrean. @return false jika penuh. */
    public boolean enqueue(String nomorAntrian, String nama, long total) {
        if (isFull()) {
            return false;
        }
        Node baru = new Node(nomorAntrian, nama, total);
        if (rear == null) {
            front = baru;
            rear = baru;
        } else {
            rear.next = baru;
            rear = baru;
        }
        size++;
        return true;
    }

    /**
     * Keluarkan pelanggan paling depan. Node dilepas dari antrean (next = null)
     * sehingga struktur Queue tidak rusak. @return null jika antrean kosong.
     */
    public Node dequeue() {
        if (front == null) {
            return null;
        }
        Node keluar = front;
        front = front.next;
        if (front == null) { // antrean menjadi kosong
            rear = null;
        }
        keluar.next = null;
        size--;
        return keluar;
    }

    /** Tampilkan antrean dari depan ke belakang. */
    public void display() {
        System.out.println("Antrian saat ini:");
        if (front == null) {
            System.out.println("(Antrian kosong)");
        }
        int no = 1;
        for (Node n = front; n != null; n = n.next) {
            System.out.println(no++ + ". " + n.nomorAntrian + " | " + n.namaPelanggan
                    + " | " + n.rupiah());
        }
        System.out.println("Jumlah dalam antrian: " + size + "/" + MAKS);
    }
}
