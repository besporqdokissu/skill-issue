import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int[][] arr =
                {
                {0, 1, 1, 0, 1},
                {0, 1, 0, 0, 1},
                {0, 1, 0, 0, 0},
                {1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0}
                };

        System.out.printf("og matrix \n%s \n\n", matPrinter(arr));

        System.out.printf("is Equivalence Relation: %b\n", isEquivalenceRelation(arr));
        System.out.printf("is Partical Order: %b\n", isParticalOrder(arr));
        System.out.printf("is Strict Order: %b\n", isStrictOrder(arr));
        System.out.printf("is Reflexive: %b\n", isReflexive(arr));
        System.out.printf("is Symetric: %b\n", isSymetric(arr));
        System.out.printf("is Transitive: %b\n\n", isTransitive(arr));

        System.out.printf("og matrix \n%s \n\n", matPrinter(arr));

        System.out.printf("симитричне замикання: \n%s\n",
                matPrinter
                (symetricalZamikanna(arr)));

        System.out.printf("транзитивне замикання: \n%s\n",
                matPrinter
                (transitiveZamikanna(arr)));

        System.out.printf("рефлексивне замикання: \n%s\n",
                matPrinter
                (reflexivZamikanna(arr)));

        System.out.printf("2 degree matrix \n%s\n\n",
                matPrinter
                        (getMatrixDegree(arr, 2)));

        System.out.printf("3 degree matrix \n%s\n\n",
                matPrinter
                        (getMatrixDegree(arr, 3)));

//Транзитивное заміканние

    }

    public static int[][] getMatrixDegree(int[][] arr, int degree){

        int[][] matrix = copyMatrix(arr);
        for(int i = 0; i < degree; i++){
            matrix = matrixMultiply(matrix, arr);

        }
        return matrix;
    }

    //Чи є задане відношення відношенням еквівалентності
    public static boolean isEquivalenceRelation(int[][] a){
        return isReflexive(a).equals("Матриця Рефлексивна")
                && isSymetric(a).equals("Матриця симетрична")
                && isTransitive(a).equals("Матриця є транзитивною");
    }
    //Чи є задане відношення частковим порядком
    public static boolean isParticalOrder(int[][] a){
        return isReflexive(a).equals("Матриця Рефлексивна")
                && isSymetric(a).equals("Матриця антисеметрична")
                && isTransitive(a).equals("Матриця є транзитивною");
    }
    //Чи є задане відношення строгим порядком
    public static boolean isStrictOrder(int[][] a){
        return isReflexive(a).equals("Матриця Антірефлексивна")
                && isSymetric(a).equals("Матриця асеметрична")
                && isTransitive(a).equals("Матриця є транзитивною");
    }

    public static String isReflexive(int[][] mat) {
        int first = mat[0][0];
        for (int i = 0; i < Math.min(mat.length, mat[0].length); i++) {
            if (mat[i][i] != first) return "Матриця не Рефлексивна і не Антірефлексивна";
        }
        if (first == 0) return "Матриця Антірефлексивна";
        return "Матриця Рефлексивна";

    }

    public static String isSymetric(int[][] mat) {
        boolean symetric = true;
        boolean antissymetric = true;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] != mat[j][i]) {
                    symetric = false;
                } else if (mat[i][j] == mat[j][i] && mat[i][j] == 1 && i != j) {
                    antissymetric = false;
                }
            }
        }
        if (symetric) return "Матриця симетрична";
        // Асиметричная - антисиметриная + антирефлексивная
        if (antissymetric && isReflexive(mat).equals("Матриця Антірефлексивна")) return "Матриця асеметрична";
        else return "Матриця антисеметрична";

    }

    public static String isTransitive(int[][] mat) {
        int min = Math.min(mat.length, mat[0].length);
        boolean transitive = true;
        boolean antitransitive = true;

        for(int i = 0; i < min; i++)
            for(int j = 0; j < min; j++)
                for(int k = 0; k < min; k++){
                    if(mat[i][j] == 1 && mat[j][k] == 1 && mat[i][k] != 1) transitive = false;
                    if(mat[i][j] ==1 && mat[j][k] == 1 && mat[i][k] == 1 && (i!=k || i!= j)) antitransitive = false;
                }

        if(transitive) return "Матриця є транзитивною";
        if (antitransitive) return "Матриця є антитранзитивною";
        return "Матриця не Транзитивна і не Антітранзитивна";
    }

    //Симетрическое замікание
    static int[][] symetricalZamikanna(int[][] mat) {
        return union(mat, transponate(mat));
    }
    //Транзитивное заміканние
    static int[][] transitiveZamikanna(int[][] mat) {
        int[][] pow = matrixMultiply(mat, mat);
        int[][] res = union(pow, mat);
        for(int i = 0; i < mat.length-2; i++){
            pow = matrixMultiply(pow, mat);
            res = union(res, pow);
        }

        return res;
    }
    //Рефлексивное замікание
    static int[][] reflexivZamikanna(int[][] mat) {
        int[][] I = {
                {1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1}
        };
        return union(mat, I);
    }

    private static int[][] matrixMultiply(int[][] mat1, int[][] mat2) {
        int[][] res = new int[mat1.length][mat2[0].length];
        for (int i = 0; i < mat1.length; i++) {
            for (int k = 0; k < mat2[0].length; k++) {
                if (mult(mat1, mat2, i, k) > 0) {
                    res[i][k] = 1;
                } else res[i][k] = 0;
            }


        }
        return res;
    }

    private static int mult(int[][] mat1, int[][] mat2, int i, int k) {
        int res = 0;
        for (int j = 0; j < mat1.length; j++) {
            res += mat1[i][j] * mat2[j][k];
        }

        return res;
    }

    private static int[][] union(int[][] mat1, int[][] mat2) {
        int[][] res;
        int min = Math.min(mat1.length, mat2.length);
        if(mat1.length > mat2.length) res = mat1.clone();
        else res = mat2.clone();
        for (int i = 0; i < min; i++) {
            for (int k = 0; k < min; k++) {
                if (mat1[i][k] == 1 || mat2[i][k] == 1)
                    res[i][k] = 1;
            }
        }
        return res;
    }

    private static int[][] transponate(int[][] mat) {
        int[][] res = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int k = 0; k < mat[0].length; k++) {
                res[i][k] = mat[k][i];
            }
        }
        return res;
    }

    static String matPrinter(int[][] mat) {
        StringBuilder matrix = new StringBuilder();
        for (int[] el : mat)
            matrix.append(Arrays.toString(el)).append("\n");
        return matrix.toString();
    }

    private static int[][] copyMatrix(int origin_Matrix[][])
    {
        int copy[][] = new int[origin_Matrix.length][origin_Matrix[0].length];
        for ( int i = 0 ; i < origin_Matrix.length; ++i)
        {
            System.arraycopy(origin_Matrix[i], 0, copy[i], 0, origin_Matrix[i].length);
        }
        return copy;
    }
}
