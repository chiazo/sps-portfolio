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

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import com.google.sps.data.Comment;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/login")
public class UserLogin extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      UserService us = UserServiceFactory.getUserService();
      
      if (us.isUserLoggedIn()) {
          String email = us.getCurrentUser().getEmail();
          String redirectUrl = "/comment.html";
          String logout = us.createLogoutURL(redirectUrl);
          Entity userEntity = new Entity("User");
          userEntity.setProperty("email", email);

          response.getWriter().println("<p>hi " + email + "<p>");
          response.getWriter().println("<p>Logout <a href=\"" + logout + "\">here</a>.</p>");

      } else {
        // String email = us.getCurrentUser().getEmail();
          String redirectUrl = "/comment.html";
          String login = us.createLoginURL(redirectUrl);

          response.getWriter().println("<p>Login <a href=\"" + login + "\">here</a>.</p>");
      }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
   response.sendRedirect("/comment.html");
  }
 

  }

  
