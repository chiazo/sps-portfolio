// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    private String[] retrieveComments() {
        ArrayList<String> comments = new ArrayList<String>();
        comments.add("user1: hey what's up");
        comments.add("user2: no way, you're joking!");
        comments.add("user3: wow, great job.");
        return comments.toArray(new String[comments.size()]);
    } 

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;");
    // response.getWriter().println("Hello Chiazo!");
    // response.getWriter().println(retrieveComments());
    String[] all_comments = retrieveComments();
    for (String c : all_comments) {
        response.getWriter().println(c);
    }
    
  }

}
