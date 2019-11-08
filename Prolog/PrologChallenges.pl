%Test case: last(X, [a,b,c,d]). Returns X = d.
last(X, [X]).
last(X, [_|T]) :- last(X, T). %Recursion to find the last element in the list

%Test case: nextLast(X, [a,b,c,d]). Returns X = c.
nextLast(X, [X, _]).
nextLast(X, [_|T]) :- nextLast(X, T). %Basically the same as the last recursion above just with nextLast instead of last...if I knew how to make functions, I would make this into one

%Test case: kelement([a,b,c,d,e,f,g], 5, X). Returns X = e.
kelement([H|_], 1, H) :- !. %Base case, if the k-index requested is 1, return the first element in the list.
kelement([_|T], K, H) :-
    K > 1,
    K1 is K-1,
    kelement(T, K1, H). %Recursion to shrink the list and decriment the k-index until it gets to 1, in which case, the kth element requested is at the head of the list.

%Test case: palin([a,b,c,c,b,a]). Returns true.
%Test case: palin([a,c,c,b,a]). Returns false.
palin([]). %Base case for if list has an even number of elements
palin([_]). %Base case for if list has odd number of elemenets
palin(Pal) :- append([H|T], [H], Pal), palin(T). %Uses append to take a list and strip the first and last element and recursively check to make sure the first unifies with the last, until there are no elements or one element left.

%Test case: flatten([a,[b,[c,d],e]],X). Returns X = [a, b, c, d, e].
flatten([],[]) :- !. %If you have two empty lists, ignore other choices, just return the empty list or true.
flatten([H|T], FlatList) :- !, flatten(H, TempHL), flatten(T, TempTL), append(TempHL, TempTL, FlatList). %Recursively break down head and tail into individual lists, then when unwinding the recursion, append those lists of size 1 into the flat list until you have one list.
flatten(T, [T]). %When flatten is called in the line above on the head above, then put that one element into its own list then backtrack.

%Test case: remove([a,b,c,d], 3, X). Returns X = [a, b, d].
remove([_|T],1,T). %Base case, if the k-index to remove is 1, then just remove the head and return the tail.
remove([H|T],K,[H|T2]) :-
    K > 1, 
    K1 is K-1,
    remove(T,K1,T2). %Recursion to shrink the list and decriment the k-index until it gets to 1, in which case, the kth element requested is removed.