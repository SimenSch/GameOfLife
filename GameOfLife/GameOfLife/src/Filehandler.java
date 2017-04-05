/**
 * Created by Simen on 21.03.2017.
 */



    import java.io.*;
import java.util.HashMap;

    /**
     * @author 4 contributor 3(javadoc by 3)
     */
    public class Filehandler {

        public String name;

        /**
         * This is the constructor
         */
        public Filehandler(){}

        /**
         *  This saves all the users, though it is not used.
         * @param userMap this HashMap is where all the registered users are.
         * userMap HashMap
         */
        public void saveAllUsers(HashMap userMap) {
            userMap.forEach((k,v)-> {
                try {
                    addUser(k.toString(),v.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        /**
         * this Method checks if the username already exists in brukere.txt
         * @param uName user name input field data.
         * uName String
         * @return
         * true/false boolean.
         * @throws IOException
         */
        public boolean userExists(String uName) throws IOException {
            BufferedReader read = new BufferedReader(new FileReader("brukere.txt"));
            String line = read.readLine();

            while (line != null) {
                int i= line.indexOf(" ");
                if(uName.equals(line.substring(0,i))){
                    return true;
                }
                line = read.readLine();
            }
            return false;
        }

        /**
         * this method verifies the password and the username when the login button is pressed.
         * @param uName username input
         * uName String
         * @param passwd password input
         * passwd String
         * @return
         * true/false boolean
         * @throws IOException
         */
        public boolean verifyLogin(String uName, String passwd) throws IOException{
            BufferedReader read = new BufferedReader(new FileReader("brukere.txt"));
            String line = read.readLine();

            while (line != null){
                int i=line.indexOf(" ");
                if (uName.equals(line.substring(0,i)) && passwd.equals(line.substring(i+1))){
                    return true;
                }
                line = read.readLine();
            }
            return false;
        }

        /**
         * this method adds the user to brukere.txt
         * @param uName username input
         * uName String
         * @param passwd password input
         * passwd String
         * @return
         * true/false boolean
         * @throws IOException
         */
        public boolean addUser(String uName, String passwd) throws IOException{
            if(!userExists(uName)){
                try(FileWriter fw = new FileWriter("brukere.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw))
                {
                    out.println(uName+" "+passwd);
                    return true;
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            return false;
        }

        /**
         * this method is not used, but will create a HashMap for all the users from brukere.txt
         * @return
         * userMap HashMap
         * @throws IOException
         */
        public HashMap<String, String> getAllUsers() throws IOException {
            BufferedReader read = new BufferedReader(new FileReader("brukere.txt"));
            HashMap<String,String> userMap = new HashMap<>();
            String line = read.readLine();

            while (line != null) {
                int i= line.indexOf(" ");
                userMap.put(line.substring(0,i),line.substring(i+1));
                line = read.readLine();
            }
            return userMap;
        }
    }

