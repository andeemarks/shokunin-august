(ns shokunin.rules
  (:require [clara.rules :refer :all]))

(defrecord SupportRequest [client level])

(defrecord ClientRepresentative [name client])

(defrecord DevRank [developer rank])

(defrule is-not-the-best-developer
    [DevRank (= ?dev developer) (not (= 1 rank))]
    =>
    (println ?dev "is not the best developer"))

(defrule is-not-the-worst-developer
    [DevRank (= ?dev developer) (not (= 5 rank))]
    =>
    (println ?dev "is not the worst developer"))

(defrule is-a-better-developer-than
    [DevRank (= ?dev1 developer) (= ?rank1 rank)]
    [DevRank (= ?dev2 developer) (= ?rank2 rank)]
    [:test (< ?rank1 ?rank2)]
    =>
    (println ?dev1 "is a better developer than" ?dev2))

(defrule is-neither-best-nor-worst
    [:and   [DevRank (= ?dev developer) (> 5 rank)]
            [DevRank (= ?dev developer) (< 1 rank)]]
    =>
    (println ?dev "is neither the best or worst developer"))

(defrule is-not-immediately-better-or-worst
    [DevRank (= ?dev1 developer) (= ?rank1 rank)]
    [DevRank (= ?dev2 developer) (= ?rank2 rank)]
    (println (- ?rank1 ?rank2))
    [:test (and 
                (not (== ?rank1 ?rank2)) 
                (not (< 2 (Math/abs (- ?rank1 ?rank2)))))]
    =>
    (println ?dev1 "is not immediately better or worse than" ?dev2))

(defn -main
    [& args]
    (-> (mk-session 'shokunin.rules)
        (insert 
                (->DevRank "a" 2)
                (->DevRank "c" 3)
                (->DevRank "b" 1))
        (fire-rules)))