import { Routes, Route } from 'react-router-dom'
import Home from './pages/Home.jsx'
import BadgeView from './pages/BadgeView.jsx'
import TopBar from './components/TopBar.jsx'

export default function App() {
  return (
    <div className="app-shell">
      <BackgroundGlow />
      <TopBar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/badge/:id" element={<BadgeView />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </div>
  )
}

function BackgroundGlow() {
  return (
    <div className="bg-glow" aria-hidden="true">
      <span className="orb orb-coral" />
      <span className="orb orb-teal" />
      <span className="dot-grid" />
    </div>
  )
}

function NotFound() {
  return (
    <div className="page-center">
      <div className="glass-panel not-found">
        <div className="eyebrow">404</div>
        <h1>That page wandered off to the beach.</h1>
        <a className="btn btn-primary" href="/">Back to the generator</a>
      </div>
    </div>
  )
}
