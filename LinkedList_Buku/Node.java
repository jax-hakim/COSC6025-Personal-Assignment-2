/**
 * Node untuk Single Linked List data buku.
 * Setiap node menyimpan kodeBuku, judul, penulis, dan pointer ke node berikutnya.
 */
public class Node {
    String kodeBuku;
    String judul;
    String penulis;
    Node next; // pointer ke node berikutnya (null jika node terakhir)

    public Node(String kodeBuku, String judul, String penulis) {
        this.kodeBuku = kodeBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.next = null;
    }
}
