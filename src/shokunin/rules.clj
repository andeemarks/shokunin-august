(ns shokunin.rules
  (:require [clara.rules :refer :all]))

(defrecord DevRankList [dev1 dev2 dev3 dev4 dev5])

(defn rank-for
    [dev-rank-list dev]
    (cond 
        (= (:dev1 dev-rank-list) dev) 1
        (= (:dev2 dev-rank-list) dev) 2
        (= (:dev3 dev-rank-list) dev) 3
        (= (:dev4 dev-rank-list) dev) 4
        (= (:dev5 dev-rank-list) dev) 5
        )
    )

(defrule all-the-rules
    [?match <- DevRankList (not (= dev1 "Jessie"))]
    [?match <- DevRankList (not (= dev5 "Evan"))]
    [?match <- DevRankList (not (= dev5 "John"))]
    [?match <- DevRankList (not (= dev5 "John"))]
    [?match <- DevRankList (= ?sarah_rank (rank-for ?match "Sarah"))]
    [?match <- DevRankList (= ?evan_rank (rank-for ?match "Evan"))]
    [?match <- DevRankList (= ?john_rank (rank-for ?match "John"))]
    [?match <- DevRankList (= ?matt_rank (rank-for ?match "Matt"))]
    [:test (< ?sarah_rank ?evan_rank)]
    [:test (< 2 (Math/abs (- ?matt_rank ?john_rank)))]
    [:test (>= 2 (Math/abs (- ?evan_rank ?john_rank)))]
    => (println "Found matching permutation in " ?match))

(defn -main
    [& args]

    ; Jessie is not the best developer
    ; Evan is not the worst developer
    ; John is not the best developer or the worst developer
    ; Sarah is a better developer than Evan
    ; Matt is not directly below or above John as a developer
    ; John is not directly below or above Evan as a developer        

    (-> (mk-session 'shokunin.rules)
        (insert 
                (->DevRankList "Sarah" "John" "Jessie" "Evan" "Matt") 
                (->DevRankList "Sarah" "John" "Jessie" "Matt" "Evan") 
                (->DevRankList "John" "Jessie" "Sarah" "Evan" "Matt") 
                )
        (fire-rules)
        ))
