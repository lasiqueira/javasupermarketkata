# Notes

Please add here any notes, assumptions and design decisions that might help up understand your though process.

###Discount Schemes: 
1- The unit discounts are applied for the same product
(2 pints of milk would get a discount, but not a pint of milk and a pack of digestives)

2- A given product only can have one type of discount scheme
(You can't have pints of milk having buy one get one for free and two items for £1 discounts at the same time)

3- Not all products have discounts

4- A basket can have multiple discounts active

###Buy one get one free:
1- If I have three items with this discount applied, I should get only one free

###Buy two items for £1:
1- If I have three items with this discount applied, the third item will be full price

2- No matter the actual price of each individual product, if this discount is applied it will be £1
for both of them
(two packs of digestives = £1, three packs of digestives = £1 + original pack of digestive price price)
###Buy three items for the price of two:
1- This discount is the same as buy one get one for free, however it requires 1 product more to activate the discount.
I buy two and I get one free. 

###Buy one kilo of vegetables for half price:
1- If the product weight surpasses 1kg, only 1kg will have the discount while the remaining weight will be full price

### Thought process
At first I started creating discounts exactly as it was in the spec,
but then I realised I could more or less make them generic and accepts number of products to activate the discount as a
parameter, among other things. This helped implement the third discount scheme very easily.

Despite extending it, I just added tests to make sure it works with the requirements.

I know I wasn't supposed to over engineer it, however I started getting a lot of fun out of it. 
