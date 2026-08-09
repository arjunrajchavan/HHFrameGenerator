import { Link } from 'react-router-dom'

export default function TopBar() {
  return (
    <header className="topbar">
      <Link to="/" className="brand">
        <span className="brand-dot" />
        HH GOA 2026
      </Link>
      <span className="topbar-tag">#FrameInGoa</span>
    </header>
  )
}
