(ns shokunin.rankable)

(defprotocol Rankable
  (rank-for       [_ dev]     "Return the numeric rank for dev")
  (first?         [_ dev]     "Return true if dev is at rank 1")
  (last?          [_ dev]     "Return true if dev rank is the lowest of all in list")
  (gap-between    [_ from to] "Return the distance between from and to in terms of rank")
  (better-than?   [_ from to] "Return true if from has a better/lower rank than to")
  (neighbours?    [_ from to] "Return true if from and to are only separated by one level of rank"))
