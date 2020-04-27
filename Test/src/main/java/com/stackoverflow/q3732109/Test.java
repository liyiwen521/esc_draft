package com.stackoverflow.q3732109;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Test {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {

        public int[][] projectdata = new int[][]{{0, 3, 3, 1, 0, 10001}, {0, 6, 1, 5, 0, 10002}, {0, 7, 9, 2, 0, 10003}, {0, 2, 2, 3, 0, 10004}, {0, 1, 1, 4, 0, 10005}};// //uses input(postion,length,width,type,status,groupid)

        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = allocateSpace(projectdata);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static String allocateSpace(int[][] projectdata) {
        final int[][] spacedata = {{1, 295, 295, 0, 0}, {2, 802, 802, 0, 0}, {3, 469, 469, 0, 0}, {4, 360, 415, 0, 0}, {5, 295, 295, 0, 0}};
        String output = " ";
        //String string = "{\"id\":1,\"method\":\"object.deleteAll\",\"params\":[\"subscriber\"]}";
        int[] store = new int[5];

        for (int i = projectdata.length - 1; i >= 0; i--) {
            for (int j = 1; j <= i; j++) {

                if (projectdata[j - 1][1] * projectdata[j - 1][2] <= projectdata[j][1] * projectdata[j][2]) {
                    store = projectdata[j - 1];
                    projectdata[j - 1] = projectdata[j];
                    projectdata[j] = store;
                }
            }

        }
        int[][] copy = new int[spacedata.length][spacedata[0].length];
        for (int i = 0; i < copy.length; i++)
            copy[i] = Arrays.copyOf(spacedata[i], spacedata[i].length);

        for (int i = 0; i < projectdata.length; i++) {
            for (int j = 0; j < copy.length; j++) {
                if (projectdata[i][1] <= copy[j][1] & projectdata[i][2] <= copy[j][2]) {
                    projectdata[i][0] = spacedata[j][0];
                    copy[j][1] = -999;
                    copy[j][2] = -999;

                    spacedata[j][3] = projectdata[i][3];
                    break;
                }
                if (j + 1 == copy.length && (projectdata[i][1] > copy[j][1] || projectdata[i][2] > copy[j][2])) {
                    projectdata[i][4] = 1;
                }

            }


        }
        int[][] output1 = new int[projectdata.length][3];
        for (int i = 0; i < projectdata.length; i++) {
            output1[i][0] = projectdata[i][0];
            output1[i][1] = projectdata[i][5];
            output1[i][2] = projectdata[i][4];


        }

        for (int m = 0; m < output1.length; m++) {
            output += Arrays.toString(output1[m]);
        }
        return output;

}}



