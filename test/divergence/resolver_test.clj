(ns divergence.resolver-test
  (:use clojure.test
        divergence.resolver))

(testing "resolve-urls"
  (deftest resolve-static-url
    (let [request  {:uri "/terms"}
          mappings {:static ["http://example.com/terms"]}]
    (is (= '("http://example.com/terms") (resolve-urls request mappings)))))

  (deftest resolve-fingerprinted-url
    (let [request {:uri     "/api/missions"
                   :headers { "Authorization" "token abcdefg123456" }}
          mapping {:static  []
                   :finger  { "abcdefg123456" "http://example.com" }}]
    (is (= '("http://example.com/api/missions") (resolve-urls request mapping)))))

  (deftest resolve-fanout-url
    (let [request {:uri     "/api/missions" :headers {}}
          mapping {:static  []
                   :finger  {}
                   :fanout  ["http://acme.example.com" "http://corp.example.com"]}]
    (is (= '("http://acme.example.com/api/missions"
             "http://corp.example.com/api/missions") (resolve-urls request mapping)))))

  (deftest resolve-no-urls
    (let [request {:uri "/api/missions" :headers {}}
          mapping {:static [] :finger {} :fanout []}]
      (is (= '() (resolve-urls request mapping))))))
