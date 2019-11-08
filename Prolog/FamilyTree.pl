%Facts:
parent(alex, aaron).
parent(alex, paulmybrother).
parent(alex, elana).
parent(judy, aaron).
parent(judy, paulmybrother).
parent(judy, elana).
parent(lily, alex).
parent(elana, chanabayla).
parent(elana, meir).
parent(pauldadsfather, alex).
parent(paulmomsfather, judy).
parent(bernice, judy).

married(judy, alex).
married(lily, pauldadsfather).
married(bernice, paulmomsfather).

male(alex).
male(paulmybrother).
male(aaron).
male(pauldadsfather).
male(paulmomsfather).
male(meir).

female(elana).
female(judy).
female(bernice).
female(lily).
female(chanabayla).

%Rules:
mother(X, Y) :- female(X), parent(X, Y).
father(X, Y) :- male(X), parent(X, Y).
grandfather(X, Y) :- male(X), parent(X, Someone), parent(Someone, Y).
grandmother(X, Y) :- female(X), parent(X, Someone), parent(Someone, Y).
sister(X, Y) :- female(X), father(Someone, X), parent(Someone, Y), X \= Y. %Must be specific here when specifying one of the parents, or each will be hit twice
brother(X, Y) :- male(X), father(Someone, X), parent(Someone, Y), X \= Y.
aunt(X, Y) :- sister(X, Someone), parent(Someone, Y).
uncle(X, Y) :- brother(X, Someone), parent(Someone, Y).
ancestor(X, Y) :- parent(X, Y).
ancestor(X, Y) :- parent(X, Someone), ancestor(Someone, Y). %Uses recursion with the base case above on line 41
son(X, Y) :- male(X), parent(Y, X).
daughter(X, Y) :- female(X), parent(Y, X).
%I may be specifying sex too often but it doesn't give bad results because of it.
%There are many ways to define these relationships but I chose ways that I would try to describe them to someone in english

/*Test Cases:

?- uncle(aaron, X).
X = chanabayla ;
X = meir ;
false.

?- ancestor(lily, X).
X = alex ;
X = aaron ;
X = paulmybrother ;
X = elana ;
false.

?- parent(alex, X).
X = aaron ;
X = paulmybrother ;
X = elana.
false.

?- ancestor(lily, aaron).
true. */