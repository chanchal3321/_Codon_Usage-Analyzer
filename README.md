# _Codon_Usage-Analyzer
This program analyze DNA sequence and compute the frequency of its constituent codons It translate the DNA, and it gives two important reports:  Codon Usage Report: Provides all unique codons, the number of each codon, and the percentage frequency in the sequence.

It maps each three-nucleotide codon, such as "ATG", to its single-letter amino acid code, for example, 'M' for Methionine.
This genetic code includes the start codon ("ATG" → 'M') and the three stop codons ("TAA", "TAG" → '-', and "TGA" → '*'). The stop codons are sometimes represented by a hyphen or asterisk in biological contexts.

Input and Validation
The main method reads a DNA sequence from the console. It allows multi-line input until a user types "
The input sequence is converted to uppercase and any whitespace is removed.
The validateDNA(String seq) method checks that the sequence contains only the valid DNA bases: A, T, G and C. It prints an error if it finds any invalid characters.

Codon Frequency Analysis
The code then loops through the in-frame DNA sequence that was validated, reading it three nucleotides at a time, i+=3 in the loop, starting from the first base, i=0.
It uses a LinkedHashMap (codonCounts) to store each unique codon and its count.
The total number of codons is tracked by a variable, totalCodons.


Output: Amino Acid Usage Report
It then sums up the counts by the amino acid that resulted, using aaCounts.
The amino acid counts are also sorted by count in descending order.
It prints a second table showing:
Amino Acid (The single-letter code)
Count (Total number of codons for that AA)
