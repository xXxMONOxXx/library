<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp"%>
<html>
<head>
    <title>Add new book</title>
</head>
<body>


<form>
  <div class="form-group">
    <label for="book_name"><fmt:message key="book.name"/></label>
    <input type="text" class="form-control" id="book_name">
  </div>
  <div class="form-group">
    <label for="book_price"><fmt:message key="book.price"/></label>
    <input type="text" class="form-control" id="book_price">
  </div>
  <div class="form-outline mb-4">
    <label for="release_date"><fmt:message key="book.release_date"/></label>
    <input type="date" id="release_date" class="form-control form-control-lg"/>
  </div>
  <div class="form-group">
    <label for="book_genres"><fmt:message key="book.genres"/></label>
    <select multiple class="form-control" id="book_genres">
      <option>1</option>
      <option>2</option>
      <option>3</option>
      <option>4</option>
      <option>5</option>
    </select>
  </div>
  <div class="form-group">
    <label for="book_info">Example textarea</label>
    <textarea class="form-control" id="book_info" rows="6"></textarea>
  </div>
  <div class="form-group">
    <label for="cover_photo"><fmt:message key="book.cover_photo"/></label>
    <input type="file" class="form-control-file" id="cover_photo">
  </div>
</form>

<form>

</form>
</body>
</html>
