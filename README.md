# Eq-Token
Java project to reify the concept of equality tokens used to implement every-day equals and hash

# Motivation

Working on [Diamond project](https://github.com/kfgodel/diamond) I found the hard way that if your objects depend
 on a lot of other objects to define their equality/hash adding them to sets or other collections may slow down
  your program.
  
The problem was that I was calculating the hash and equality on tree like structures every time an object was compared
to other, that means recursively calculating hashes for the nodes and leafs of the tree.
But at the same time those values didn't change over time.

I realized that on most applications, and most implementations where I defined a custom equals method, the same was true.
Equals/hash is defined on a subset of class fields that don't change their value over time and thus we can avoid the 
overhead.
 
# Goal

This project has two goals in mind.
**1# Simplify equals and hashcode** method implementations so you don't have to repeat your code over and over
 on different classes or projects
 
**2# Optimize implementations** by adding a little extra information so your classes run as fast as possible without
 you having to think about performance
 

# How

Custom equals/hashcode are implemented by comparing a set primitive values, or other objects. The equality of the 
 object is then delegated to a combination of other objects.
 I call that an *equality token*: the minimum set of values that you need to tell if this object is equal to others.
  
In this project the equality token is reified to be used as a delegate object that knows what your equality and hashcode is,
   and does that with a good performance.

Depending on your object though, the token can change over time. If your equality token is based on values that 
are immutable in the lifespan of your application, then it's safe to assume that your hashcode will stay the same, and
     that compared values can be cached.
     If the values used for your equality token change, you will need to provide a way for the token to get updated.
