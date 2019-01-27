//CS 320 Homework 1
//Nkengla Muluh Awa Junior


import java.util.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.*;

public class RegexAssignment {
    public static void main(String[] args) throws Exception {


        URLConnection bc = new URL("https://www.communitytransit.org/busservice/schedules/").openConnection();

        bc.setRequestProperty("user-Agent", "Mozilla/5.0(Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko)Chrome/23.0.1271.95 Safari/537.11");

        BufferedReader in = new BufferedReader(new InputStreamReader(bc.getInputStream(), Charset.forName("UTF-8")));

        String input = "";
        String text = "";
        while ((input = in.readLine()) != null) {
            text += input + "\n";
        }

        in.close();
        methodOne(text);
    }
    //The method does the first portion of the Assignment by
    //asking the user for the first letter of his Destination
    //and prints the cities and with their bus numbers
        public static void methodOne(String text) {

            ///regex for the Destinations
            Pattern pattern = Pattern.compile("<h3>(.*)</h3>(?s)(.+?)/>");
            Matcher match = pattern.matcher(text);
            ///regex for the bus stops
            Pattern pattern2 = Pattern.compile("<strong><a href=\"(.*)\".*>(.*)</a></strong>");

            Scanner scan = new Scanner(System.in);
            System.out.print("Please enter a letter that your destinations start with: ");
            String letter = scan.next();

            while (match.find()) {

                if (match.group(1).startsWith(letter)) {
                    System.out.println("Destination: " + match.group(1));

                    Matcher match1 = pattern2.matcher(match.group(2));

                    while (match1.find()) {
                        System.out.println("Bus Number: " + match1.group(2));
                    }
                    System.out.println("++++++++++++++++++++++++++++++++++++++");
                }
            }
            //handling the exception which may be thrown by
            //methodTwo
            try {
                methodTwo(text, pattern2, pattern); ///calling methodTwo
            }catch (Exception e){
                System.out.println("Your Exception is "+e);
            }
        }
        //This method asks the user to input the Route Id as a string and it prints
        //the link of the route which was input
        //The method also establishes a URL Connection with the link
        public static void methodTwo(String text,Pattern pattern2,Pattern pattern) throws Exception {

            Scanner scan = new Scanner(System.in);
            System.out.print("Please enter a route ID as a string: ");
            String routeID = scan.next();
            String weblink = "";
            Matcher one = pattern2.matcher(text);
            while (one.find()) {
                if (one.group(2).equals(routeID)) {
                    weblink = "https://www.communitytransit.org/busservice" + one.group(1);
                    System.out.println("The link for your route is: " + weblink);
                    break;
                }
            }
            System.out.println();

            URLConnection conn = new URL(weblink).openConnection();//establishing a new connection for the URL of the link

            conn.setRequestProperty("user-Agent", "Mozilla/5.0(Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko)Chrome/23.0.1271.95 Safari/537.11"); // setting the connection request

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));

            String line = "";
            String a = "";
            while ((line = in.readLine()) != null) {
                a += line + "\n";
            }
            in.close();//closing the connection
            methodThree(a); ///calling method3
        }

        //This method prints out the stop numbers as well as the stop destinations
        //which are visited by the route which was input by the user
        public static void methodThree(String a) {
            ////regex for the title
            Pattern p = Pattern.compile("<h2>.*>(.*)<.*</h2>(?s)(.+?)</thead>");
            Matcher math = p.matcher(a);

            ////while loop for the destination
            while (math.find()) {
                System.out.println("Destination: " + math.group(1).replace("&amp;", "&"));
                ///regex for the stop numbers and the stop places
                Pattern pn = Pattern.compile("<strong.*>(.*)</strong>\\s*.*\\s*<p>(.*)</p>\\s*</th\\s*");
                Matcher mt = pn.matcher(math.group(2));

                ///while loop for the stop numbers
                while (mt.find()) {
                    System.out.println("Stop number: " + mt.group(1) + " is " + mt.group(2).replace("&amp;", "&"));
                }
                System.out.println("++++++++++++++++++++++++++++++++++++++++");
            }
        }

    }

