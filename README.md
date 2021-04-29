##What is this application?
It is small Java banking application that helps the user to create a bank account.
- The user can transfer money from his/her account to any another account. 
- The user get generate account summary, which gives the balance and all the transactions. 
- The user can check his/her balance. 

##What do I need to know before running this application? 
- Ensure you have MySQL database stored. 
- Change the username in spring.datasource.username = <change here>
- Change the password in spring.datasource.password = <change here>

##Get started 
- Simply run com.monese.bank.BankApplication
- Ensure your application loads up properly, you should see something like this in Console log, Started BankApplication in 10.697 seconds (JVM running for 11.946)
- If it is good then now 8080 port is open in your local machine  
- Now, check if two tables, Account and Transaction are created in your database server. I used MySQL Workbench. 
- Visit, http://localhost:8080/swagger-ui.html
- For your ease, I have injected swagger to perform all the acitivites we discussed in "What is this application" section. 

##Now let the APIs do the magic
There are four APIs. 
- api/saveAccount : Adds a new account
- api/balance : Returns balance of an account
- api/transfer : Allows you to transfer money from one account to another 
- api/accountStatement : Returns balance and all the transactions, similiar to the bank statements 

Step 1: Lets assume we are opening accounts for Tom and Jerry. They both starts with £100 bank balance in their Monese bank. 
- Use endpoint api/saveAccount. Use swagger endpoint or below JSON to create two accounts. Please done it one by one. 
- Change the createdAt if you prefer
- After you submit, store the id returned for each of the account. We will use this id to perform other activities. 
- Tom id should be 1 
- Jerry id should be 2

Do this first
{
  "balance": 100,
  "createdAt": "2021-04-29T20:44:06.836Z",
  "id": 0,
  "name": "Tom"
}
Now do this
{
  "balance": 100,
  "createdAt": "2021-04-29T20:44:06.836Z",
  "id": 0,
  "name": "Jerry"
}

Step 2: Now, lets check if their bank balances are £100 each. 
- Use endpoint api/balance. 
- Simple input the accountId, we recorded in Step 1 in the accountId form and hit submit. 

Step 3: Lets assume Tom gives £10 to Jerry. Here, he will be using following endpoint. 
- api/transfer
- API takes the senderId, senderAmount and receiverId. Here, senderId = 1 (Tom id), senderAmount = £10 and receiverId = 2 (Jerry id)
- Ir returns Tom's bank balance 

Step 4: Lets assume Tom wants to check his account summary. Here, he will be using following endpoint. 
- api/accountStatement
- Simply input Tom id (1) in the input box 
- It returns Balance and full transactions 



















