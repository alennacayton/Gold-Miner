public class GoldMiner {

    static public void openGameMenu(){

        GameMenu game = new GameMenu();

    }

    static public void importFiles(){

/*
            File inputFile;
            Account c;
            GovernmentOfficial g;
            ContactTracer t;


            try {
                inputFile = new File("coordinates.txt");
                Scanner reader = new Scanner(inputFile);

                while (reader.hasNext()) {

                    String username = reader.next();
                    String type = reader.next();

                    if (type.equals("citizen"))
                    {
                        c = new Account (username, "123456");
                        acclist.add(c);
                        reader.nextLine();
                    }

                    else if (type.equals("official"))
                    {
                        g = new GovernmentOfficial (username, "123456");
                        acclist.add(g);
                        reader.nextLine();
                    }

                    else if (type.equals("tracer"))
                    {
                        t = new ContactTracer(username, "123456");
                        acclist.add(t);
                        reader.nextLine();
                    }

                }
                reader.close();

            }

            catch (FileNotFoundException ex) {
                System.out.println("File does not exist!");
                System.exit(1);
            }

*/

    }


    public static void main (String [] args)
    {

        importFiles();
        openGameMenu();

      //  GameFrame frame = new GameFrame();

    }
}
