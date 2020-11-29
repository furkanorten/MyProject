/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package MyProject;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import static spark.Spark.port;


public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {

        Logger logger = LogManager.getLogger(App.class);

        int port = Integer.parseInt(System.getenv("PORT"));
        port(port);
        logger.error("Current port number:" + port);
    
        port(getHerokuAssignedPort());

        get("/", (req, res) -> "Hello, World! \nYou will be given two input boxes in the compute page. \n Give numbers for arraylist to the first box. \nGive two numbers to the second box. \nFirst will be for searched value and second will be for how many times it exists. \n Go to http://localhost:4567/compute");

        post("/compute", (req, res) -> {
          //System.out.println(req.queryParams("input1"));
          //System.out.println(req.queryParams("input2"));

          String input1 = req.queryParams("input1");
          java.util.Scanner sc1 = new java.util.Scanner(input1);
          sc1.useDelimiter("[;\r\n]+");
          java.util.ArrayList<Integer> inputList = new java.util.ArrayList<>();
          while (sc1.hasNext())
          {
            int value = Integer.parseInt(sc1.next().replaceAll("\\s",""));
            inputList.add(value);
          }
          sc1.close();
          System.out.println(inputList);


          String input2 = req.queryParams("input2");
          java.util.Scanner sc2 = new java.util.Scanner(input2);
          sc2.useDelimiter("[;\r\n]+");
          int input2AsInt = Integer.parseInt(sc2.next().replaceAll("\\s", ""));
          int input3AsInt = Integer.parseInt(sc2.next().replaceAll("\\s", ""));   

          boolean result = App.search(inputList, input2AsInt, input3AsInt);

          Map<String, Boolean> map = new HashMap<String, Boolean>();
          map.put("result", result);
          return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());


        get("/compute",
            (rq, rs) -> {
              Map<String, String> map = new HashMap<String, String>();
              map.put("result", "not computed yet!");
              return new ModelAndView(map, "compute.mustache");
            },
            new MustacheTemplateEngine());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }


    public static boolean search(ArrayList<Integer> array, int a, int b) {
        System.out.println("Searching " + a + " for " + b + "times...");
        if (array == null) return false;
        
        int count_a = 0;
        for (int i : array) {
          if (i == a) count_a++;
        }

        if(count_a == b) return true;
        else return false;
               
        
      }    
}
