(ns divergence.static-test
  (:use clojure.test
        divergence.static))

(def known-routes ["http://example.com/api/terms"])

(testing "known-route?"
  (deftest matches-known-route
    (is (known-route? "api/terms" (first known-routes))))

  (deftest does-not-match-known-route
    (is (not (known-route? "api/random" (first known-routes))))))

(testing "expand-routes"
  (deftest expands-known-route
    (is (=
          (first (expand-routes "api/terms" known-routes))
          "http://example.com/api/terms")))

  (deftest ignores-unknown-route
     (is (empty? (expand-routes "api/unknown" known-routes)))))
