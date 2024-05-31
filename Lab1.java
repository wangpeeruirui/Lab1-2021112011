//王鹏睿B2分支修改
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Lab1 {
    private static final ArrayList<String> arr = new ArrayList<>();
    private static final HashMap<String, Integer> vertexToIdx = new HashMap<>();
    private static final HashMap<Integer, String> idxToVertex = new HashMap<>();
    private static final ArrayList<ArrayList<Integer>> graph = new ArrayList<>(100);

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入文件路径：");
        String path = scanner.nextLine();
        process(path);
        for (String s : vertexToIdx.keySet()) {
            idxToVertex.put(vertexToIdx.get(s), s);
        }

        int vertex_num = vertexToIdx.size();
        for (int i = 0; i < vertex_num; i++) {
            ArrayList<Integer> row = new ArrayList<>(100);
            for (int j = 0; j < vertex_num; j++) {
                row.add(0);
            }
            graph.add(row);
        }
        int edge_num = arr.size() - 1;
        for (int i = 0; i < edge_num; i++) {
            int cnt = graph.get(vertexToIdx.get(arr.get(i))).get(vertexToIdx.get(arr.get(i + 1)));
            graph.get(vertexToIdx.get(arr.get(i))).set(vertexToIdx.get(arr.get(i + 1)), cnt + 1);
        }

        int choice;
        do {
            choice = scanner.nextInt();
            scanner.nextLine();  // 清除输入缓冲区中的回车
            String word1, word2;
            switch (choice) {
                case 1:
                    showDirectedGraph();
                    break;
                case 2:
                    System.out.println("接下来进行桥接词查询");
                    System.out.print("请输入第一个词：");
                    word1 = scanner.nextLine();
                    System.out.print("请输入第二个词：");
                    word2 = scanner.nextLine();
                    System.out.println(queryBridgeWords(word1, word2));
                    break;
                case 3:
                    System.out.println("接下来根据桥接词生成新文本");
                    System.out.print("请输入文本：");
                    String inputText = scanner.nextLine();
                    System.out.println(generateNewText(inputText));
                    break;
                case 4:
                    System.out.println("接下来计算两个单词间的最短路径");
                    System.out.print("请输入第一个词：");
                    word1 = scanner.nextLine();
                    System.out.print("请输入第二个词：");
                    word2 = scanner.nextLine();
                    System.out.println(calcShortestPath(word1, word2));
                    break;
                case 5:
                    System.out.println("接下来进行随机游走生成文本");
                    System.out.println(randomWalk());
                    break;
                default:
                    break;
            }
        } while (choice >= 1 && choice <= 5);
    }

    private static void process(String path) {
        StringBuilder str = new StringBuilder(300);
        try {
            FileReader fr = new FileReader(path);
            int data;
            while ((data = fr.read()) != -1) {
                char ch = (char)data;
                if (!Character.isLetter(ch) && ch != ' ')  continue;
                str.append(ch);
            }
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int i = 0, m = str.length(), cnt = 0;
        while (i < m) {
            int j = i;
            while (j < m && str.charAt(j) != ' ')  j++;
            String s = str.substring(i, j).toLowerCase();
            arr.add(s);
            if (!vertexToIdx.containsKey(s)) {
                vertexToIdx.put(s, cnt);
                cnt++;
            }
            while (j < m && str.charAt(j) == ' ')  j++;
            i = j;
        }
    }

    private static void showDirectedGraph() {
        int n = graph.size();
        Graphviz gv = new Graphviz();
        gv.addln(gv.start_graph());
        for (Integer i : idxToVertex.keySet()) {
            gv.addln(i + " [label = \"" + idxToVertex.get(i) + "\"]");
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (graph.get(i).get(j) > 0)  gv.addln(i + " -> " + j + " [label = \"" + graph.get(i).get(j) + "\"]");
            }
        }
        gv.addln(gv.end_graph());
        String type = "jpg";
        File out = new File("graph." + type);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
        System.out.println("已成功生成并存储有向图的图形化表示");
    }

    private static String queryBridgeWords(String word1, String word2) {
        if (!vertexToIdx.containsKey(word1) && !vertexToIdx.containsKey(word2))
            return "No \"" + word1 + "\" and \"" + word2 + "\" in the graph!";
        if (!vertexToIdx.containsKey(word1))  return "No \"" + word1 + "\" in the graph!";
        if (!vertexToIdx.containsKey(word2))  return "No \"" + word2 + "\" in the graph!";
        ArrayList<String> list = new ArrayList<>();
        int n = graph.size(), idx1 = vertexToIdx.get(word1), idx2 = vertexToIdx.get(word2);
        for (int j = 0; j < n; j++) {
            if (graph.get(idx1).get(j).equals(0))  continue;
            if (graph.get(j).get(idx2).compareTo(0) > 0) {
                list.add(idxToVertex.get(j));
            }
        }
        if (list.isEmpty())  return "No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!";
        StringBuilder str = new StringBuilder("The bridge words from \"" + word1 + "\" to \"" + word2 + "\" ");
        int wordCount = list.size();
        str.append(wordCount == 1 ? "is: " : "are: ");
        for (int i = 0; i < wordCount - 1; i++) {
            str.append(list.get(i));
            str.append(',');
        }
        if (wordCount > 1) {
            str.append("and ");
        }
        str.append(list.get(wordCount - 1));
        str.append('.');
        return str.toString();
    }

    private static String generateNewText(String inputText) {
        StringBuilder str = new StringBuilder(200);
        String[] list = inputText.split(" ");
        str.append(list[0]);
        str.append(' ');
        for (int i = 1; i < list.length; i++) {
            String ret = queryBridgeWords(list[i - 1].toLowerCase(), list[i].toLowerCase());
            if (ret.charAt(0) != 'N') {
                int startIdx = ret.indexOf(':') + 2, endIdx = startIdx;
                while (ret.charAt(endIdx) != ',' && ret.charAt(endIdx) != '.')  endIdx++;
                str.append(ret, startIdx, endIdx);
                str.append(' ');
            }
            str.append(list[i]);
            str.append(' ');
        }
        str.deleteCharAt(str.length() - 1);
        return str.toString();
    }

    private static String calcShortestPath(String word1, String word2) {
        if (word1.equals(word2))  return "Please input two different words!";
        if (!vertexToIdx.containsKey(word1) && !vertexToIdx.containsKey(word2))
            return "No \"" + word1 + "\" and \"" + word2 + "\" in the graph!";
        if (!vertexToIdx.containsKey(word1))  return "No \"" + word1 + "\" in the graph!";
        if (!vertexToIdx.containsKey(word2))  return "No \"" + word2 + "\" in the graph!";
        int vertexNum = graph.size(), startIdx = vertexToIdx.get(word1), endIdx = vertexToIdx.get(word2);
        int[] dis = new int[vertexNum];
        Arrays.fill(dis, 1000);
        dis[startIdx] = 0;
        int[] pre = new int[vertexNum];
        for (int i = 0; i < vertexNum; i++)  pre[i] = i;
        for (int j = 0; j < vertexNum; j++) {
            if (graph.get(startIdx).get(j).compareTo(0) > 0){
                dis[j] = graph.get(startIdx).get(j);
                pre[j] = startIdx;
            }
        }
        boolean flag = false;
        for (int i = 0; i < vertexNum; i++) {
            int t = -1;
            for (int j = 0; j < vertexNum; j++) {
                if (dis[j] < 1000 && (t == - 1 || dis[j] < dis[t]))  t = j;
            }
            if (t == -1)  break;
            if (t == endIdx) {
                flag = true;
                break;
            }
            for (int j = 0; j < vertexNum; j++) {
                int x = graph.get(t).get(j).equals(0) ? 1000 : graph.get(t).get(j);
                if (dis[t] + x < dis[j]) {
                    dis[j] = dis[t] + x;
                    pre[j] = t;
                }
            }
            dis[t] = 1000;
        }
        if (!flag)  return "No path from \"" + word1 + "\" to \"" + word2 + "\"!";
        int idx = endIdx;
        ArrayList<String> list = new ArrayList<>();
        list.add(word2);
        do {
            list.add(idxToVertex.get(pre[idx]));
            idx = pre[idx];
        } while (pre[idx] != idx);
        StringBuilder str = new StringBuilder(300);
        int len = list.size();
        str.append(list.get(len - 1));
        for (int i = len - 2; i >= 0; i--) {
            str.append("->");
            str.append(list.get(i));
        }
        return "The shortest path from \"" + word1 + "\" to \"" + word2 + "\" is: " + str;
    }

    private static String randomWalk() throws IOException {
        int vertexNum = graph.size();
        ArrayList<ArrayList<ArrayList<Integer>>> adjTable = new ArrayList<>(200);
        for (ArrayList<Integer> integers : graph) {
            ArrayList<ArrayList<Integer>> row = new ArrayList<>(100);
            for (int j = 0; j < vertexNum; j++) {
                if (integers.get(j).compareTo(0) > 0) {
                    ArrayList<Integer> edge = new ArrayList<>();
                    edge.add(j);
                    edge.add(0);
                    row.add(edge);
                }
            }
            adjTable.add(row);
        }
        Random rand = new Random();
        int idx = rand.nextInt(vertexNum);
        StringBuilder str = new StringBuilder(300);
        while (!adjTable.get(idx).isEmpty()) {
            str.append(idxToVertex.get(idx));
            str.append(' ');
            int next = rand.nextInt(adjTable.get(idx).size());
            if (adjTable.get(idx).get(next).get(1).equals(1))  break;
            adjTable.get(idx).get(next).set(1, 1);
            idx = adjTable.get(idx).get(next).get(0);
        }
        str.deleteCharAt(str.length() - 1);
        String outputText = str.toString();
        try {
            FileWriter fw = new FileWriter("out.txt");
            fw.write(outputText);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputText;
    }
}
