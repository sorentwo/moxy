# moxy

Internal API traffic routing.

## Overview

Designed to efficiently route http traffic, more specifically API requests,
through to individual servers and proxy the response.

## Cascading Rule System

1. Process request
2. Attempt matching against all rule types
3. Process response

### Static

Map paths to a known domain and url. Static mappings would be the norm for
legal documents such as the terms of service or documentation. For a static
route to resolve the server must be taught about the `server/route` tuple, where
`server` is a fully qualified url and `route` is a simple relative glob.

```clojure
["https://example.com/api/content/guides"
 "https://example.com/api/terms"]
```

### Fingerprinted

Fingerprinted routing relies on the presence of a fingerprint in the request
header. Fingerprint extraction can be taught with a `header/pattern` tuple,
where `header` is a the name of a request header and pattern is a regular
expression that captures the fingerprint.

```clojure
{ :header "Authorization", :pattern "token (\w+)" }
```

Captured fingerprints are then matched against a list of known servers and
traffic is routed to the first (hopefully only) match.

In order to collect fingerprint -> server mappings any focus rules are applied
to the response from a fanout. In this way the server can incrementally learn
routes without relying on synchronization with outside sources.

### Fanout

Fanout is the default behavior when a request is determined not to be
statically routable or focused. A sample fanout request/response cycle would be
something like this:

1. A request is made `https://api.example.com/sessions`.
2. The request is determined not to be static or focused, defaulting to fanout.
3. Fanout returns a set of all known servers combined with the uri.
4. The fanout simultaneously performs requests to all registered servers.
5. If there are any successful response the first response is selected and
   forwarded back to the requester.
6. Fingerprinting rules are applied to the response, mapping back to the server
   if possible.

Because we expect servers to have exclusive rights to any user, fanout is only
designed to return a single response. No contatenation of responses will occur.

### Teaching

Routing rules are backed by Redis, so they persist between deployments. You
teach the app about available servers and routes is via the REPL.

```clojure
(use 'moxy.routes)

; Add a static route
(add-static "https://example.com" "api/content/guides")

; Add a fingerprint extraction rule
(add-fingerprint "Authorization" "token (\w+)")

; Add a server for fanout
(add-fanout "https://acme.example.com")
```

## Local Deploy

Ensure that Redis is running, then:

```bash
cp .env.sample .env
lein deps
foreman start
```

## Heroku Deploy

```bash
DEPLOY=production/staging
heroku create dscout-router-$DEPLOY -s cedar

git push $DEPLOY
```

## License

Copyright © 2013 dScout, Inc
