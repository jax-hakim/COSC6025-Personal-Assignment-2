/**
 * Node data pelanggan, dipakai oleh Queue maupun Stack.
 * Menyimpan nomorAntrian, namaPelanggan, totalBelanja, dan pointer next.
 */
public class Node {
    String nomorAntrian;
    String namaPelanggan;
    long totalBelanja;
    Node next; // pointer ke node berikutnya

    public Node(String nomorAntrian, String namaPelanggan, long totalBelanja) {
        this.nomorAntrian = nomorAntrian;
        this.namaPelanggan = namaPelanggan;
        this.totalBelanja = totalBelanja;
        this.next = null;
    }

    /** Format rupiah sederhana, contoh: 125000 -> Rp125.000 */
    public String rupiah() {
        String s = Long.toString(totalBelanja);
        StringBuilder sb = new StringBuilder();
        int hitung = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            sb.insert(0, s.charAt(i));
            if (++hitung % 3 == 0 && i > 0) {
                sb.insert(0, '.');
            }
        }
        return "Rp" + sb;
    }
}
