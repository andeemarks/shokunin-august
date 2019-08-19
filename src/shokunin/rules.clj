(ns shokunin.rules
    (:require [clara.rules :refer :all]))

(defprotocol Rankable 
    (rank-for [_ dev])
    (gap-between [_ from to])
    )

(defrecord DevRankList [dev1 dev2 dev3 dev4 dev5] 
    Rankable
    (rank-for [_ dev]
        (cond 
            (= dev1 dev) 1
            (= dev2 dev) 2
            (= dev3 dev) 3
            (= dev4 dev) 4
            (= dev5 dev) 5 ) )
    (gap-between [_ from to]
        (let 
            [rank1 (rank-for _ from)
                rank2 (rank-for _ to)]
                (Math/abs (- rank1 rank2))))
    )

(defrecord Match [match])

(defrule rules
    [?match <- DevRankList (not (= dev1 "Jessie"))]
    [?match <- DevRankList (not (= dev5 "Evan"))]
    [?match <- DevRankList (not (= dev1 "John"))]
    [?match <- DevRankList (not (= dev5 "John"))]
    [?match <- DevRankList (= ?sarah_rank (rank-for ?match "Sarah"))]
    [?match <- DevRankList (= ?evan_rank (rank-for ?match "Evan"))]
    [?match <- DevRankList (= ?john_rank (rank-for ?match "John"))]
    [?match <- DevRankList (= ?matt_rank (rank-for ?match "Matt"))]
    [:test (< ?sarah_rank ?evan_rank)]
    [:test (<= 2 (gap-between ?match "Matt" "John"))]
    [:test (<= 2 (gap-between ?match "Evan" "John"))]
    => (insert! (->Match ?match)))

(defquery get-match
    []
    [?match <- Match])

(def facts
        [(->DevRankList "Sarah","John","Jessie","Evan","Matt") ; winner!
        (->DevRankList "John","Sarah","Jessie","Evan","Matt")
        (->DevRankList "Jessie","Sarah","John","Evan","Matt")
        (->DevRankList "Sarah","Jessie","John","Evan","Matt")
        (->DevRankList "John","Jessie","Sarah","Evan","Matt")
        (->DevRankList "Jessie","John","Sarah","Evan","Matt")
        (->DevRankList "Jessie","John","Evan","Sarah","Matt")
        (->DevRankList "John","Jessie","Evan","Sarah","Matt")
        (->DevRankList "Evan","Jessie","John","Sarah","Matt")
        (->DevRankList "Jessie","Evan","John","Sarah","Matt")
        (->DevRankList "John","Evan","Jessie","Sarah","Matt")
        (->DevRankList "Evan","John","Jessie","Sarah","Matt")
        (->DevRankList "Evan","Sarah","Jessie","John","Matt")
        (->DevRankList "Sarah","Evan","Jessie","John","Matt")
        (->DevRankList "Jessie","Evan","Sarah","John","Matt")
        (->DevRankList "Evan","Jessie","Sarah","John","Matt")
        (->DevRankList "Sarah","Jessie","Evan","John","Matt")
        (->DevRankList "Jessie","Sarah","Evan","John","Matt")
        (->DevRankList "John","Sarah","Evan","Jessie","Matt")
        (->DevRankList "Sarah","John","Evan","Jessie","Matt")
        (->DevRankList "Evan","John","Sarah","Jessie","Matt")
        (->DevRankList "John","Evan","Sarah","Jessie","Matt")
        (->DevRankList "Sarah","Evan","John","Jessie","Matt")
        (->DevRankList "Evan","Sarah","John","Jessie","Matt")
        (->DevRankList "Matt","Sarah","John","Jessie","Evan")
        (->DevRankList "Sarah","Matt","John","Jessie","Evan")
        (->DevRankList "John","Matt","Sarah","Jessie","Evan")
        (->DevRankList "Matt","John","Sarah","Jessie","Evan")
        (->DevRankList "Sarah","John","Matt","Jessie","Evan")
        (->DevRankList "John","Sarah","Matt","Jessie","Evan")
        (->DevRankList "John","Sarah","Jessie","Matt","Evan")
        (->DevRankList "Sarah","John","Jessie","Matt","Evan")
        (->DevRankList "Jessie","John","Sarah","Matt","Evan")
        (->DevRankList "John","Jessie","Sarah","Matt","Evan")
        (->DevRankList "Sarah","Jessie","John","Matt","Evan")
        (->DevRankList "Jessie","Sarah","John","Matt","Evan")
        (->DevRankList "Jessie","Matt","John","Sarah","Evan")
        (->DevRankList "Matt","Jessie","John","Sarah","Evan")
        (->DevRankList "John","Jessie","Matt","Sarah","Evan")
        (->DevRankList "Jessie","John","Matt","Sarah","Evan")
        (->DevRankList "Matt","John","Jessie","Sarah","Evan")
        (->DevRankList "John","Matt","Jessie","Sarah","Evan")
        (->DevRankList "Sarah","Matt","Jessie","John","Evan")
        (->DevRankList "Matt","Sarah","Jessie","John","Evan")
        (->DevRankList "Jessie","Sarah","Matt","John","Evan")
        (->DevRankList "Sarah","Jessie","Matt","John","Evan")
        (->DevRankList "Matt","Jessie","Sarah","John","Evan")
        (->DevRankList "Jessie","Matt","Sarah","John","Evan")
        (->DevRankList "Evan","Matt","Sarah","John","Jessie")
        (->DevRankList "Matt","Evan","Sarah","John","Jessie")
        (->DevRankList "Sarah","Evan","Matt","John","Jessie")
        (->DevRankList "Evan","Sarah","Matt","John","Jessie")
        (->DevRankList "Matt","Sarah","Evan","John","Jessie")
        (->DevRankList "Sarah","Matt","Evan","John","Jessie")
        (->DevRankList "Sarah","Matt","John","Evan","Jessie")
        (->DevRankList "Matt","Sarah","John","Evan","Jessie")
        (->DevRankList "John","Sarah","Matt","Evan","Jessie")
        (->DevRankList "Sarah","John","Matt","Evan","Jessie")
        (->DevRankList "Matt","John","Sarah","Evan","Jessie")
        (->DevRankList "John","Matt","Sarah","Evan","Jessie")
        (->DevRankList "John","Evan","Sarah","Matt","Jessie")
        (->DevRankList "Evan","John","Sarah","Matt","Jessie")
        (->DevRankList "Sarah","John","Evan","Matt","Jessie")
        (->DevRankList "John","Sarah","Evan","Matt","Jessie")
        (->DevRankList "Evan","Sarah","John","Matt","Jessie")
        (->DevRankList "Sarah","Evan","John","Matt","Jessie")
        (->DevRankList "Matt","Evan","John","Sarah","Jessie")
        (->DevRankList "Evan","Matt","John","Sarah","Jessie")
        (->DevRankList "John","Matt","Evan","Sarah","Jessie")
        (->DevRankList "Matt","John","Evan","Sarah","Jessie")
        (->DevRankList "Evan","John","Matt","Sarah","Jessie")
        (->DevRankList "John","Evan","Matt","Sarah","Jessie")
        (->DevRankList "Jessie","Evan","Matt","Sarah","John")
        (->DevRankList "Evan","Jessie","Matt","Sarah","John")
        (->DevRankList "Matt","Jessie","Evan","Sarah","John")
        (->DevRankList "Jessie","Matt","Evan","Sarah","John")
        (->DevRankList "Evan","Matt","Jessie","Sarah","John")
        (->DevRankList "Matt","Evan","Jessie","Sarah","John")
        (->DevRankList "Matt","Evan","Sarah","Jessie","John")
        (->DevRankList "Evan","Matt","Sarah","Jessie","John")
        (->DevRankList "Sarah","Matt","Evan","Jessie","John")
        (->DevRankList "Matt","Sarah","Evan","Jessie","John")
        (->DevRankList "Evan","Sarah","Matt","Jessie","John")
        (->DevRankList "Sarah","Evan","Matt","Jessie","John")
        (->DevRankList "Sarah","Jessie","Matt","Evan","John")
        (->DevRankList "Jessie","Sarah","Matt","Evan","John")
        (->DevRankList "Matt","Sarah","Jessie","Evan","John")
        (->DevRankList "Sarah","Matt","Jessie","Evan","John")
        (->DevRankList "Jessie","Matt","Sarah","Evan","John")
        (->DevRankList "Matt","Jessie","Sarah","Evan","John")
        (->DevRankList "Evan","Jessie","Sarah","Matt","John")
        (->DevRankList "Jessie","Evan","Sarah","Matt","John")
        (->DevRankList "Sarah","Evan","Jessie","Matt","John")
        (->DevRankList "Evan","Sarah","Jessie","Matt","John")
        (->DevRankList "Jessie","Sarah","Evan","Matt","John")
        (->DevRankList "Sarah","Jessie","Evan","Matt","John")
        (->DevRankList "John","Jessie","Evan","Matt","Sarah")
        (->DevRankList "Jessie","John","Evan","Matt","Sarah")
        (->DevRankList "Evan","John","Jessie","Matt","Sarah")
        (->DevRankList "John","Evan","Jessie","Matt","Sarah")
        (->DevRankList "Jessie","Evan","John","Matt","Sarah")
        (->DevRankList "Evan","Jessie","John","Matt","Sarah")
        (->DevRankList "Evan","Jessie","Matt","John","Sarah")
        (->DevRankList "Jessie","Evan","Matt","John","Sarah")
        (->DevRankList "Matt","Evan","Jessie","John","Sarah")
        (->DevRankList "Evan","Matt","Jessie","John","Sarah")
        (->DevRankList "Jessie","Matt","Evan","John","Sarah")
        (->DevRankList "Matt","Jessie","Evan","John","Sarah")
        (->DevRankList "Matt","John","Evan","Jessie","Sarah")
        (->DevRankList "John","Matt","Evan","Jessie","Sarah")
        (->DevRankList "Evan","Matt","John","Jessie","Sarah")
        (->DevRankList "Matt","Evan","John","Jessie","Sarah")
        (->DevRankList "John","Evan","Matt","Jessie","Sarah")
        (->DevRankList "Evan","John","Matt","Jessie","Sarah")
        (->DevRankList "Jessie","John","Matt","Evan","Sarah")
        (->DevRankList "John","Jessie","Matt","Evan","Sarah")
        (->DevRankList "Matt","Jessie","John","Evan","Sarah")
        (->DevRankList "Jessie","Matt","John","Evan","Sarah")
        (->DevRankList "John","Matt","Jessie","Evan","Sarah")
        (->DevRankList "Matt","John","Jessie","Evan","Sarah")])

(defn -main
    [& args]

    ; Jessie is not the best developer
    ; Evan is not the worst developer
    ; John is not the best developer or the worst developer
    ; Sarah is a better developer than Evan
    ; Matt is not directly below or above John as a developer
    ; John is not directly below or above Evan as a developer        

    (-> (mk-session 'shokunin.rules)
        (insert-all facts)
        (fire-rules)
        (query get-match)
        (println))
    )
