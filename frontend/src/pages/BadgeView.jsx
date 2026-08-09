import { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import { getBadge, NotFoundError } from '../api.js'
import ResultPanel from '../components/ResultPanel.jsx'
import GeneratingStatus from '../components/GeneratingStatus.jsx'

export default function BadgeView() {
  const { id } = useParams()
  const [state, setState] = useState({ status: 'loading', badge: null, error: null })

  useEffect(() => {
    let cancelled = false
    setState({ status: 'loading', badge: null, error: null })

    getBadge(id)
      .then((badge) => {
        if (!cancelled) setState({ status: 'success', badge, error: null })
      })
      .catch((err) => {
        if (cancelled) return
        if (err instanceof NotFoundError) {
          setState({ status: 'not-found', badge: null, error: null })
        } else {
          setState({ status: 'error', badge: null, error: err.message })
        }
      })

    return () => {
      cancelled = true
    }
  }, [id])

  return (
    <main className="wrap wrap-single">
      <section className="preview-col preview-col-wide">
        <div className="canvas-shell">
          {state.status === 'loading' && <GeneratingStatus />}

          {state.status === 'success' && <ResultPanel badge={state.badge} />}

          {state.status === 'not-found' && (
            <EmptyState
              title="This badge doesn't exist"
              body="It may have been generated on a different environment, or the link is off."
            />
          )}

          {state.status === 'error' && (
            <EmptyState title="Couldn't load this badge" body={state.error || 'Something went wrong.'} />
          )}
        </div>
      </section>
    </main>
  )
}

function EmptyState({ title, body }) {
  return (
    <div className="empty-state">
      <div className="eyebrow">oops</div>
      <h2>{title}</h2>
      <p className="sub">{body}</p>
      <Link className="btn btn-primary" to="/">
        Make your own badge
      </Link>
    </div>
  )
}
