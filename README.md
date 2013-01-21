# divergence

Internal API traffic routing.

## Overview

Designed to efficiently route http traffic, more specifically API requests,
through to individual servers and proxy the response.

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
