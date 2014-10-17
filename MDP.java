import java.io.*;
import java.util.*;
/**
 * 
 * 
 * @author Darshan Thaker
 * @version 
 */
public class MDP 
{
    private final File fFile;
    int i = 0;
    static int length = 0;
    static ArrayList<String> notes = new ArrayList<String>();
    static ArrayList<Double> times = new ArrayList<Double>();
    static HashMap<String, ArrayList<String>> noteshash = new HashMap<String, ArrayList<String>>();
    static HashMap<Double, ArrayList<Double>> timeshash = new HashMap<Double, ArrayList<Double>>();
    static int right = 0;
    
    /**
     * Main method that opens the two .txt files and checks the answers.
     */
    public static void main(String[] aArgs) throws FileNotFoundException {
        MDP input = new MDP("notes.txt");
        //MDP output = new MDP("answer.txt");
        input.processLineByLine();
        
        createMDP();
        choosefromMDP();
    }

    /**
     * Constructor ReadWithScanner.
     */
    public MDP(String aFileName){
        fFile = new File(aFileName);  
    }

    /**
     * Processes the scanner in the given file by calling the processLine() method.
     */
    public final void processLineByLine() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(fFile));
        i = length;
        try 
        {
            while ( scanner.hasNextLine() )
            {
                processLine(scanner.nextLine());
            }
        }
        finally {
            length = i ;
            scanner.close();
        }
    }

    /**
     * Goes through each line in the scanner and assigns the array to those values.
     */
    protected void processLine(String aLine)
    {
        Scanner scanner = new Scanner(aLine);
        if (scanner.hasNext())
        {
            String note = scanner.next();
            notes.add(note);
            double time = Double.parseDouble(scanner.next());
            times.add(time);
        }
        else {
            System.out.println("Empty or invalid line. Unable to process.");
        }
        scanner.close();
    }
    
    /**
     * Creates Hashmap of Arraylists(each possible note that follows one note) 
     * 
     * Ex. Sequence given is A->B->A->B->A->C 
     * At the end, hash of A contains (B,B, C) and hash of B contains (A, A) and hash of C contains nothing. 
     */
    public static void createMDP()
    {
        //HashMap<String, ArrayList<String>> hash = new HashMap<String, ArrayList<String>>();
        //ArrayList<String> intermediatelist = new ArrayList<String>();
        
        for (int i = 0; i < notes.size() - 1; i++)
        {
            if (noteshash.get(notes.get(i)) != null)
            {
                noteshash.get(notes.get(i)).add(notes.get(i + 1));
                ArrayList<String> intermediatelist = noteshash.get(notes.get(i));
                //System.out.printf("MAP %s to %s\n", notes.get(i), intermediatelist);
                noteshash.put(notes.get(i), intermediatelist); 
            }
            else
            {
                ArrayList<String> intermediatelist = new ArrayList<String>();
                intermediatelist.add(notes.get(i + 1));
                //System.out.printf("MAP %s to %s\n", notes.get(i), intermediatelist);
                noteshash.put(notes.get(i), intermediatelist);
            }
    
        }
        for (int i = 0; i < times.size() - 1; i++)
        {
            if (timeshash.get(times.get(i)) != null)
            {
                timeshash.get(times.get(i)).add(times.get(i + 1));
                ArrayList<Double> intermediatelist = timeshash.get(times.get(i));
                timeshash.put(times.get(i), intermediatelist);
            }
            else
            {
                ArrayList<Double> intermediatelist = new ArrayList<Double>();
                intermediatelist.add(times.get(i + 1));
                timeshash.put(times.get(i), intermediatelist);
            }
        }
        
        //System.out.println(hash.values());
    }
    
    /**
     * Chooses randomly from Hashmap one note by note.
     * 
     * Ex. Start at A and then randomly choose a note(assume that the note chosen is B). Then go to the hashmap of B and choose a note randomly (keeps going)
     */
    public static void choosefromMDP()
    {
        String initnote = "C";
        Double inittime = 0.1;
        // Sound Network
        System.out.println("SinOsc sine => dac;");

        System.out.println("[50, 52, 53, 55, 57, 59, 60, 62] @=> int n[];");

        System.out.println("0 => int A;");
        System.out.println("1 => int B;");
        System.out.println("2 => int C;");
        System.out.println("3 => int D;");
        System.out.println("4 => int E;");
        System.out.println("5 => int F;");
        System.out.println("6 => int G;");
        System.out.println("7 => int A2;");

        System.out.println("0.50 => sine.gain;");

        for (int i = 0; i < 30; ++i)
        {
            if (noteshash.get(initnote) == null || timeshash.get(inittime) == null)
            {
                initnote = "C";
                inittime = 0.1;
            }
            ArrayList<String> noteslist= noteshash.get(initnote);
            ArrayList<Double> timeslist = timeshash.get(inittime);
            Random rand = new Random();
            int noteindex = rand.nextInt(noteslist.size());
            int timeindex = rand.nextInt(timeslist.size());
            System.out.printf("Std.mtof(n[%s] + 12) => sine.freq; 0.70 => sine.gain; 0.25::second => now; 0=>sine.gain; %s::second=>now;\n", noteslist.get(noteindex),
                                                                                                                                           timeslist.get(timeindex));
            //System.out.printf("New note %s played for %s seconds\n", noteslist.get(noteindex), timeslist.get(timeindex));
            initnote = noteslist.get(noteindex);
            inittime = timeslist.get(timeindex);
        }
    }
} 
