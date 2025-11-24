import java.util.*;
import java.io.*;

class CodonUsageAnalyzer {

    private static final Map<String, Character> CODON_TABLE = createCodonTable();

    private static Map<String, Character> createCodonTable(){
        Map<String, Character> map = new HashMap<>();
        // same table as earlier
        map.put("ATG", 'M');
        map.put("TTT", 'F'); map.put("TTC", 'F');
        map.put("TTA", 'L'); map.put("TTG", 'L'); map.put("CTT", 'L'); map.put("CTC", 'L'); map.put("CTA", 'L'); map.put("CTG", 'L');
        map.put("ATT", 'I'); map.put("ATC", 'I'); map.put("ATA", 'I');
        map.put("GTT", 'V'); map.put("GTC", 'V'); map.put("GTA", 'V'); map.put("GTG", 'V');
        map.put("TCT", 'S'); map.put("TCC", 'S'); map.put("TCA", 'S'); map.put("TCG", 'S'); map.put("AGT", 'S'); map.put("AGC", 'S');
        map.put("CCT", 'P'); map.put("CCC", 'P'); map.put("CCA", 'P'); map.put("CCG", 'P');
        map.put("ACT", 'T'); map.put("ACC", 'T'); map.put("ACA", 'T'); map.put("ACG", 'T');
        map.put("GCT", 'A'); map.put("GCC", 'A'); map.put("GCA", 'A'); map.put("GCG", 'A');
        map.put("TAT", 'Y'); map.put("TAC", 'Y');
        map.put("CAT", 'H'); map.put("CAC", 'H');
        map.put("CAA", 'Q'); map.put("CAG", 'Q');
        map.put("AAT", 'N'); map.put("AAC", 'N');
        map.put("AAA", 'K'); map.put("AAG", 'K');
        map.put("GAT", 'D'); map.put("GAC", 'D');
        map.put("GAA", 'E'); map.put("GAG", 'E');
        map.put("TGT", 'C'); map.put("TGC", 'C');
        map.put("TGG", 'W');
        map.put("CGT", 'R'); map.put("CGC", 'R'); map.put("CGA", 'R'); map.put("CGG", 'R'); map.put("AGA", 'R'); map.put("AGG", 'R');
        map.put("GGT", 'G'); map.put("GGC", 'G'); map.put("GGA", 'G'); map.put("GGG", 'G');
        map.put("TAA", '-'); map.put("TAG", '-'); map.put("TGA", '*');
        return Collections.unmodifiableMap(map);
    }

    private static boolean validateDNA(String seq){
        if(seq == null || seq.isEmpty()) return false;
        for(char c: seq.toUpperCase(Locale.ROOT).toCharArray()) if("ATGC".indexOf(c) < 0) return false;
        return true;
    }

    public static void main(String[] args) throws IOException{
        System.out.println("=== Codon Usage Frequency Analyzer ===");
        System.out.println("Paste DNA sequence (type END on a new line to finish):");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null){
            if(line.trim().equalsIgnoreCase("END")) break;
            sb.append(line.trim().replaceAll("\\s+", ""));
        }
        String dna = sb.toString().toUpperCase(Locale.ROOT);
        if(!validateDNA(dna)){
            System.err.println("Invalid DNA sequence. Only A/T/G/C allowed.");
            return;
        }
        Map<String, Integer> codonCounts = new LinkedHashMap<>();
        int totalCodons = 0;
        for(int i=0;i+2<dna.length();i+=3){
            String codon = dna.substring(i, i+3);
            codonCounts.put(codon, codonCounts.getOrDefault(codon, 0) + 1);
            totalCodons++;
        }
        System.out.println("Total codons counted: " + totalCodons);
        System.out.println("Codon\tCount\tFrequency%\tAminoAcid");
        // Sort by count desc
        List<Map.Entry<String,Integer>> list = new ArrayList<>(codonCounts.entrySet());
        list.sort((a,b)->b.getValue().compareTo(a.getValue()));
        for(Map.Entry<String,Integer> e: list){
            String codon = e.getKey();
            int cnt = e.getValue();
            double freq = (cnt * 100.0) / totalCodons;
            char aa = CODON_TABLE.containsKey(codon) ? CODON_TABLE.get(codon) : 'X';
            System.out.printf("%s\t%d\t%.2f\t%c\n", codon, cnt, freq, aa);
        }
        // Also output amino acid usage by mapping codons to amino acids
        Map<Character, Integer> aaCounts = new LinkedHashMap<>();
        for(Map.Entry<String,Integer> e: codonCounts.entrySet()){
            char aa = CODON_TABLE.containsKey(e.getKey()) ? CODON_TABLE.get(e.getKey()) : 'X';
            aaCounts.put(aa, aaCounts.getOrDefault(aa,0) + e.getValue());
        }
        System.out.println("\nAminoAcid\tCount\tFrequency%");
        List<Map.Entry<Character,Integer>> aal = new ArrayList<>(aaCounts.entrySet());
        aal.sort((a,b)->b.getValue().compareTo(a.getValue()));
        for(Map.Entry<Character,Integer> e: aal){
            double freq = (e.getValue() * 100.0) / totalCodons;
            System.out.printf("%c\t%d\t%.2f\n", e.getKey(), e.getValue(), freq);
        }
    }
}
