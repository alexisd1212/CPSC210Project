

# Project: Simple Online Store

### *What the application will do* 

- The goal of the Simple Online Store is to give your users the capability of selecting a product to purchase, 
viewing purchase information, adding it to an online shopping cart, and finally, actually purchasing the products in 
the shopping cart.

### *Who will use it*

- Adults in all age groups (as they have the ability to pay) as the online store will have a plethora of 
inventory in different categories that would be of interest to all age groups
 (almost acts like Amazon).
 
 ### *Why this project interest me*
 
 - Online shopping has quickly become the preference of many people 
 due to its **convenience**. Moreover, online stores which have a **range of product
 categories** which are easier to find like Amazon, provides further convenience as one doesn't have to go to multiple 
 stores to get different products. During this covid era, eCommerce has become such a lifeline for people 
 that an application like this would clearly be relevant and prove itself **useful in any domain**.
 
### *User stories*
- User can click on a product on the Landing Page to display the corresponding product's image.
- User can see a Product Details page displayed when the ‘Select item’ button is clicked showing the same information from 
the product card, but also a unique product id, a long description, ‘Add to Cart’ button, and a ‘See More Products’ 
button.
- User can click 'Add item to cart' if they want and have option to click on the ‘See More Products’ page to return to the Products Page
or 'Place Order' if they're ready. 
- User can view their cart to display the all the items added to your Shopping Cart page containing the product id, name, 
price, and quantity ordered input box for each product previously added to the Shopping Cart.
- User can see a total purchase amount on the Shopping Cart that is calculated as the sum of the quantities multiplied 
by the unit price for each product ordered.
- User can click a ‘Checkout’ button on the Shopping Cart Page to complete the order. User will see a confirmation 
number when the order has been placed.
- User can click a ‘Remove’ button on the Shopping Cart Page to remove a selcted item from their cart
  ####**Bonus features**
- User can see shipping charges added to the total purchase amount
- User can see sales taxes added to the total purchase amount
- Developer will implement the product inventory in an external file or a database.

### *Phase 4: Task 2*
-I decided to implement robustness into my ProductItem class where I created a checked
 Exception named **NotInStockException** in the constructor which is thrown when an item is
 not in stock **(i.e item inventory = 0)**. Thus, all classes which call or use a ProductItem object will 
 also throw that exception (OnlineStore, ProductList, JsonReader)

### *Phase 4: Task 3*
- Based on the different functions in each class and the actual implementation of my GUI, 
I think I would have refactored how the user would checkout. Specifically instead of them directly typing
each detail of their personal details, credit card, address etc. I could have created a separate Login/User 
class which already would hold the user's details and then plug that into my gui. That way the user can login at checkout
and it would have simplified the checkout process since the GUI would already have that information.