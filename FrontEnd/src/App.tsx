import React, { useState, useEffect } from 'react';
import './App.css';

interface Artist {
  artist: string;
  description: string;
  url: string;
  alt: string;
}

export default function Gallery() {
  const [index, setIndex] = useState(0);
  const [showMore, setShowMore] = useState(false);
  const [artistList, setArtistList] = useState<Artist[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetch('http://localhost:8080/enriquez/personalities') // Replace with your actual API endpoint
      .then(response => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then(data => {
        setArtistList(data);
        setLoading(false);
      })
      .catch(error => {
        console.error("Could not fetch artists:", error);
        setError(error.message); 
        setLoading(false);
      });
  }, []); 

  const hasNext = index < artistList.length - 1;
  const hasPrevious = index > 0;
  const currentArtist = artistList[index];

  function handleNextClick() {
    setIndex(prevIndex => (hasNext ? prevIndex + 1 : 0));
  }

  function handlePreviousClick() {
    setIndex(prevIndex => (hasPrevious ? prevIndex - 1 : artistList.length - 1));
  }

  function handleMoreClick() {
    setShowMore(prevShowMore => !prevShowMore);
  }

  if (loading) {
    return <div style={{ textAlign: 'center', padding: '20px' }}>Loading artists...</div>;
  }

  if (error) {
    return <div style={{ textAlign: 'center', padding: '20px', color: 'red' }}>Error loading artists: {error}</div>;
  }

  if (!currentArtist) {
    return <div style={{ textAlign: 'center', padding: '20px' }}>No artists available.</div>;
  }

  return (
    <div style={{ textAlign: 'center', padding: '20px' }}>
      <h1>Justine Jay A. Enriquez</h1>

      <div style={{ marginBottom: '20px' }}>
        <button onClick={handlePreviousClick} disabled={!hasPrevious}>
          Back
        </button>
        <button onClick={handleNextClick} disabled={!hasNext}>
          Next
        </button>
      </div>

      <div style={{ marginBottom: '20px' }}>
        <button onClick={handleMoreClick}>
          {showMore ? 'Hide' : 'Show'} details
        </button>
      </div>

      <h2>{currentArtist.artist}</h2>
      <h3>({index + 1} of {artistList.length})</h3>

      {showMore && (
        <p style={{ margin: '10px', padding: '10px', border: '1px solid #ccc', borderRadius: '5px' }}>
          {currentArtist.description}
        </p>
      )}

      <img
        src={currentArtist.url}
        alt={currentArtist.alt}
        style={{ width: '200px', height: '250px', objectFit: 'cover', borderRadius: '8px', margin: '10px' }}
      />
    </div>
  );
}