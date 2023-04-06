
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <title>Movie</title>
  <style>
      li {
          padding: 5px;
      }
      table {
          width: 100%;
          text-align: center;
      }
  </style>
</head>

<body>
  <jsp:include page="/header.jsp" />

  <ul>
      <li id="id">Id: 2341</li>
      <li id="name">Name: Galaxy S21</li>
      <li id="providerName">Provider Name: Phone Provider</li>
      <li id="price">Price: 21000000</li>
      <li id="categories">Categories: Technology, Phone</li>
      <li id="rating">Rating: 8.3</li>
      <li id="inStock">In Stock: 17</li>
  </ul>

  <label>Add Your Comment:</label>
  <form action="" method="post">
      <input type="text" name="comment" value="" />
      <button type="submit">submit</button>
  </form>
  <br>
  <form action="" method="POST">
      <label>Rate(between 1 and 10):</label>
      <input type="number" id="quantity" name="quantity" min="1" max="10">
      <button type="submit">Rate</button>
  </form>
  <br>
  <form action="" method="POST">
      <button type="submit">Add to BuyList</button>
  </form>
  <table>
      <caption><h2>Comments</h2></caption>
      <tr>
          <th>username</th>
          <th>comment</th>
          <th>date</th>
          <th>likes</th>
          <th>dislikes</th>
      </tr>
      <tr>
          <td>user1</td>
          <td>Good</td>
          <td>2022-07-25</td>
          <td>
              <form action="" method="POST">
                  <label for="">2</label>
                  <input
                          id="form_comment_id"
                          type="hidden"
                          name="comment_id"
                          value="1"
                  />
                  <button type="submit">like</button>
              </form>
          </td>
          <td>
              <form action="" method="POST">
                  <label for="">1</label>
                  <input
                          id="form_comment_id"
                          type="hidden"
                          name="comment_id"
                          value="-1"
                  />
                  <button type="submit">dislike</button>
              </form>
          </td>
      </tr>
  </table>
  <table>
      <caption><h2>Suggested Commodities</h2></caption>
      <tr>
          <th>Id</th>
          <th>Name</th>
          <th>Provider Name</th>
          <th>Price</th>
          <th>Categories</th>
          <th>Rating</th>
          <th>In Stock</th>
          <th>Links</th>
      </tr>
  </table>
</body>
</html>